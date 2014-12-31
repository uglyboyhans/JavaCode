package object;

import file.*;

import javax.swing.*;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.filechooser.FileSystemView;
import javax.swing.tree.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
//Tree
public class Tree {
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				JFrame frame = new JFrame();
				FileTree fileTree = new FileTree();
				
				FileTreeModel model = new FileTreeModel(
						new DefaultMutableTreeNode(new FileNode("root", null,
								null, true)));
				fileTree.setModel(model);
				fileTree.setCellRenderer(new FileTreeRenderer());

				frame.getContentPane().add(new JScrollPane(fileTree),
						BorderLayout.CENTER);
				frame.setSize(300, 700);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
			}
		});
	}
}


//FileTree
class FileTree extends JTree {
	public TreePath mouseInPath;
	protected FileSystemView fileSystemView = FileSystemView
			.getFileSystemView();
	File current;//记录当前选中的文件或文件夹
	DefaultMutableTreeNode root;//记录当前节点
	JPopupMenu pop = new JPopupMenu();//右键菜单
	JMenuItem deleteItem = new JMenuItem("Delete");
	JMenuItem renameItem = new JMenuItem("Rename");
    JMenuItem aboutItem = new JMenuItem("About");
    JMenuItem newFileItem=new JMenuItem("CreateFile");
    JMenuItem newFolderItem=new JMenuItem("CreateFolder");
    JMenuItem copyItem=new JMenuItem("CopyTo");
    JMenuItem cutItem=new JMenuItem("CutTo");

	public FileTree() {
		
		setRootVisible(false);
		pop.add(newFileItem);//
		pop.addSeparator();
		pop.add(newFolderItem);//
		pop.addSeparator();
		pop.add(copyItem);//
		pop.addSeparator();
		pop.add(cutItem);//
		pop.addSeparator();
		pop.add(deleteItem);//
		pop.addSeparator();
		pop.add(aboutItem);
		
		//create file
		newFileItem.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseReleased(MouseEvent e){
				System.out.println(current.getPath());//test
				String newfilename=JOptionPane.showInputDialog("Please enter file's name").trim();
				CreateFile.createFile(current.getPath()+"\\"+newfilename);
			}
		});
		
		//new folder
		newFolderItem.addMouseListener(new MouseAdapter(){
			public void mouseReleased(MouseEvent e){
				System.out.println(current.getPath());//test
				String newfilename=JOptionPane.showInputDialog("Please enter folder's name").trim();
				CreateFolder.createFolder(current.getPath()+"\\"+newfilename);
			}
		});
		
		//copy
		copyItem.addMouseListener(new MouseAdapter(){
			public void mouseReleased(MouseEvent e){
				System.out.println(current.getPath());//test
				String goal=JOptionPane.showInputDialog("where you want to paste?").trim();
				if(current.isDirectory()){
					try {
						CopyFolder.copyFolder(current.getPath(), goal);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				if(current.isFile()){
					try {
						CopyFile.copyFile(current.getPath(), goal);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		
		//cut
		cutItem.addMouseListener(new MouseAdapter(){
			public void mouseReleased(MouseEvent e){
				System.out.println(current.getPath());//test
				String goal=JOptionPane.showInputDialog("where you want to paste?").trim();
				if(current.isDirectory()){//剪切文件夹
					try {
						CopyFolder.copyFolder(current.getPath(), goal);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					DeleteFolder.delFolder(current.getPath());//复制完了就删除文件夹
					
				}
				if(current.isFile()){//剪切文件
					try {
						CopyFile.copyFile(current.getPath(), goal);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					DeleteFile.delete(new File(current.getPath()));	//复制完了就删除文件
				}
			}
		});
		
		//delete file
		deleteItem.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseReleased(MouseEvent e){
				System.out.println(current.getPath());//test
				if(current.isFile())
				DeleteFile.delete(new File(current.getPath()));	
				else{
					DeleteFolder.delFolder(current.getPath());
				}
			}
		});
		
		//about
		aboutItem.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseReleased(MouseEvent e){
				System.out.println(current.getPath());//test
				JFrame jf=new JFrame("about");//窗口
				jf.setSize(500, 300);
				jf.setLocationRelativeTo(null);
				jf.setVisible(true);
				JTextArea jt=new JTextArea();//显示属性的区域
				jf.getContentPane().add(new JScrollPane(jt),
						BorderLayout.CENTER);//TextArea加到滚动条里

					jt.append("f: isFile\n");
					jt.append("d: isDirector\n");
					jt.append("r: canRead\n");
					jt.append("w: canWrite\n");
					jt.append(Attribute.attribute(current)+"\n");
					jt.append("last modified time:"+LastModified.lastModified(current)+"\n");
							
			}
		});
		
		addTreeWillExpandListener(new TreeWillExpandListener() {//如果要扩张才会继续加载文件节点
			@Override
			public void treeWillExpand(TreeExpansionEvent event)
					throws ExpandVetoException {
				DefaultMutableTreeNode lastTreeNode = (DefaultMutableTreeNode) event
						.getPath().getLastPathComponent();
				FileNode fileNode = (FileNode) lastTreeNode.getUserObject();
				if (!fileNode.isInit) {
					File[] files;
					if (fileNode.isDummyRoot) {
						files = fileSystemView.getRoots();
					} else {
						files = fileSystemView.getFiles(
								((FileNode) lastTreeNode.getUserObject()).file,
								false);
					}
					for (int i = 0; i < files.length; i++) {
						FileNode childFileNode = new FileNode(
								fileSystemView.getSystemDisplayName(files[i]),
								fileSystemView.getSystemIcon(files[i]),
								files[i], false);
						DefaultMutableTreeNode childTreeNode = new DefaultMutableTreeNode(
								childFileNode);
						lastTreeNode.add(childTreeNode);
					}
					// 通知模型节点发生变化
					DefaultTreeModel treeModel1 = (DefaultTreeModel) getModel();
					treeModel1.nodeStructureChanged(lastTreeNode);
				}
				// 更改标识，避免重复加载
				fileNode.isInit = true;
			}

			@Override
			public void treeWillCollapse(TreeExpansionEvent event)
					throws ExpandVetoException {
				DefaultMutableTreeNode lastTreeNode = (DefaultMutableTreeNode) event
						.getPath().getLastPathComponent();
				FileNode fileNode = (FileNode) lastTreeNode.getUserObject();
				
				lastTreeNode.removeAllChildren();//移除所有子节点
				fileNode.isInit = false;//更改标识来重新加载

			}
		});
		addMouseMotionListener(new MouseAdapter() {//mouse move
			@Override
			public void mouseMoved(MouseEvent e) {
				TreePath path = getPathForLocation(e.getX(), e.getY());
				if (path != null) {
					if (mouseInPath != null) {
						Rectangle oldRect = getPathBounds(mouseInPath);
						mouseInPath = path;
						repaint(getPathBounds(path).union(oldRect));
					} else {
						mouseInPath = path;
						Rectangle bounds = getPathBounds(mouseInPath);
						repaint(bounds);
					}
				} else if (mouseInPath != null) {
					Rectangle oldRect = getPathBounds(mouseInPath);
					mouseInPath = null;
					repaint(oldRect);
				}
			}				
		});
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {//可执行文件打开
				
				if(e.getClickCount() >= 2)//如果连击
				{
					TreePath path = getPathForLocation(e.getX(), e.getY());
					if(path != null)
					{
						DefaultMutableTreeNode temp = (DefaultMutableTreeNode) path.getLastPathComponent();//获得选中节点
						if(temp == null)
							return;
						
						FileNode data = (FileNode) temp.getUserObject();//获得数据节点
						if(path != null && data.file.isFile())//如果是可执行文件
						{
							try
							{
								Runtime ce = Runtime.getRuntime();
								String Temp = new String(data.file.getParent());
								Temp = Temp + "//";
								Temp = Temp + data.file.getName();
								String cmdarray = "cmd /c start " + Temp;////应该是指令有问题
								ce.exec(cmdarray);////这里有一个严重的问题，如果可执行文件的名字有空格的话就会忽略空格后
							}
							catch(Exception ee)
							{
								System.out.println(ee);
							}
						}
					}
				}
			}
			public void mouseReleased(MouseEvent e)//鼠标松开时，如果是右击，则显示右击菜单
			{
				if(e.isPopupTrigger())//测试是否这个事件将引起一个弹出式菜单在平台中探出
				{
					TreePath tp = getPathForLocation(e.getX(),e.getY());
					//如果右击节点
					if(tp == null)
						return;
					else{
						DefaultMutableTreeNode temp = (DefaultMutableTreeNode) tp.getLastPathComponent();//获得选中节点
						if(temp == null)
							return;
						root=temp;
						FileNode data = (FileNode) temp.getUserObject();//获得数据节点
						System.out.println(data.file.getPath());
						current=data.file;
						
					
					pop.show(e.getComponent(),e.getX(),e.getY());
					}
				}
			}
		});
		
	}
}


//FIleNode
class FileNode {
	public FileNode(String name, Icon icon, File file, boolean isDummyRoot) {
		this.name = name;
		this.icon = icon;
		this.file = file;
		this.isDummyRoot = isDummyRoot;
	}

	public boolean isInit;
	public boolean isDummyRoot;
	public String name;
	public Icon icon;
	public File file;
}


//Renderer
class FileTreeRenderer extends DefaultTreeCellRenderer {
	public FileTreeRenderer() {
	}

	@Override
	public Component getTreeCellRendererComponent(javax.swing.JTree tree,
			java.lang.Object value, boolean sel, boolean expanded,
			boolean leaf, int row, boolean hasFocus) {

		FileTree fileTree = (FileTree) tree;
		JLabel label = (JLabel) super.getTreeCellRendererComponent(tree, value,
				sel, expanded, leaf, row, hasFocus);

		DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
		FileNode fileNode = (FileNode) node.getUserObject();
		label.setText(fileNode.name);
		label.setIcon(fileNode.icon);

		label.setOpaque(false);
		if (fileTree.mouseInPath != null
				&& fileTree.mouseInPath.getLastPathComponent().equals(value)) {
			label.setOpaque(true);
			label.setBackground(new Color(0,225,0,90));
		}
		return label;
	}
}


//FileTreeMolel
class FileTreeModel extends DefaultTreeModel {
	public FileTreeModel(TreeNode root) {
		super(root);
		FileSystemView fileSystemView = FileSystemView.getFileSystemView();
		File[] files = fileSystemView.getRoots();
		for (int i = 0; i < files.length; i++) {//将每个文件赋值给node
			FileNode childFileNode = new FileNode(
					fileSystemView.getSystemDisplayName(files[i]),
					fileSystemView.getSystemIcon(files[i]), files[i], false);
			DefaultMutableTreeNode childTreeNode = new DefaultMutableTreeNode(
					childFileNode);
			((DefaultMutableTreeNode) root).add(childTreeNode);
		}
	}

	@Override
	public boolean isLeaf(Object node) {
		DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) node;
		FileNode fileNode = (FileNode) treeNode.getUserObject();
		if (fileNode.isDummyRoot)
			return false;
		return fileNode.file.isFile();
	}
}
