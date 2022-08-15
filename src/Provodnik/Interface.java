
package Provodnik;

import org.apache.commons.io.FileUtils;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

import static org.apache.commons.io.FileUtils.copyFile;
import static org.apache.commons.io.FileUtils.getFile;

public class Interface extends JFrame {
    private JPanel catalogPanel = new JPanel();
    private JList filesList = new JList();
    private JScrollPane filesScroll = new JScrollPane(filesList);
    private JPanel buttonsPanel = new JPanel();
    private JButton addButton = new JButton("Создать папку");
    private JButton backButton = new JButton("Назад");
    private JButton deleteButton = new JButton("Удалить");
    private JButton renameButton = new JButton("Переименовать");
    private JButton copyButton = new JButton("Скопировать");
    private JButton enterButton = new JButton("Вставить");
    private ArrayList<String> dirsPath = new ArrayList();
    private String copyFile1;
    private String enterFile;

    public Interface() {
        super("Проводник");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        catalogPanel.setLayout(new BorderLayout(5, 5));
        catalogPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        buttonsPanel.setLayout(new GridLayout(1, 6, 5, 5));
        File discs[] = File.listRoots();
        filesScroll.setPreferredSize(new Dimension(400, 500));
        filesList.setListData(discs);
        filesList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        filesList.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    DefaultListModel model = new DefaultListModel();
                    String selectedObject = filesList.getSelectedValue().toString();
                    String fullPath = toFullPath(dirsPath);
                    File selectedFile;
                    if (dirsPath.size() > 1) {
                        selectedFile = new File(fullPath, selectedObject);
                    } else {
                        selectedFile = new File(fullPath + selectedObject);
                    }

                    if (selectedFile.isDirectory()) {
                        String[] rootStr = selectedFile.list();
                        for (String str : rootStr) {
                            File checkObject = new File(selectedFile.getPath(), str);
                            if (!checkObject.isHidden()) {
                                if (checkObject.isDirectory()) {
                                    model.addElement(str);
                                } else {
                                    model.addElement(str);
                                }
                            }
                        }
                        if (dirsPath.size() <= 1) {
                            dirsPath.add(selectedObject);
                        } else {
                            dirsPath.add("\\" + selectedObject);
                        }
                        filesList.setModel(model);
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (dirsPath.size() > 1) {
                    dirsPath.remove(dirsPath.size() - 1);
                    String backDir = toFullPath(dirsPath);
                    String[] objects = new File(backDir).list();
                    DefaultListModel backRootModel = new DefaultListModel();

                    for (String str : objects) {
                        File checkFile = new File(backDir, str);
                        if (!checkFile.isHidden()) {
                            if (checkFile.isDirectory()) {
                                backRootModel.addElement(str);
                            } else {
                                backRootModel.addElement(str);
                            }
                        }
                    }
                    filesList.setModel(backRootModel);
                } else {
                    dirsPath.removeAll(dirsPath);
                    filesList.setListData(discs);
                }
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!dirsPath.isEmpty()) {
                    String currentPath;
                    File newFolder;
                    filemanager.CreateNewFolderJDialog newFolderJDialog = new filemanager.CreateNewFolderJDialog(Interface.this);

                    if (newFolderJDialog.getReady()) {
                        currentPath = toFullPath(dirsPath);
                        newFolder = new File(currentPath, newFolderJDialog.getNewName());
                        if (!newFolder.exists())
                            newFolder.mkdir();

                        File updateDir = new File(currentPath);
                        String updateMas[] = updateDir.list();
                        DefaultListModel updateModel = new DefaultListModel();
                        for (String str : updateMas) {
                            File check = new File(updateDir.getPath(), str);
                            if (!check.isHidden()) {
                                if (check.isDirectory()) {
                                    updateModel.addElement(str);
                                } else {
                                    updateModel.addElement(str);
                                }
                            }
                        }
                        filesList.setModel(updateModel);
                    }
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedObject = filesList.getSelectedValue().toString();
                String currentPath = toFullPath(dirsPath);

                if (!selectedObject.isEmpty()) {
                    deleteDir(new File(currentPath, selectedObject));
                    File updateDir = new File(currentPath);
                    String updateMas[] = updateDir.list();
                    DefaultListModel updateModel = new DefaultListModel();

                    for (String str : updateMas) {
                        File check = new File(updateDir.getPath(), str);
                        if (!check.isHidden()) {
                            if (check.isDirectory()) {
                                updateModel.addElement(str);
                            } else {
                                updateModel.addElement(str);
                            }
                        }
                    }
                    filesList.setModel(updateModel);
                }
            }
        });


        copyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedObject = filesList.getSelectedValue().toString();
                String currentPath = toFullPath(dirsPath);
                //copyFile = (File) filesList.getSelectedValue();
                copyFile1 = currentPath + "\\" + selectedObject;
            }
        });

        enterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedObject = filesList.getSelectedValue().toString();
                String currentPath = toFullPath(dirsPath);
                enterFile = currentPath + "\\" + selectedObject;
                try {
                    copyFile(getFile(copyFile1),getFile(enterFile));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });



        renameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!dirsPath.isEmpty() & filesList.getSelectedValue() != null) {

                    String currentPath = toFullPath(dirsPath);
                    String selectedObject = filesList.getSelectedValue().toString();
                    RenameJDialog renamer = new RenameJDialog(Interface.this);

                    if (renamer.getReady()) {
                        File renameFile = new File(currentPath, selectedObject);
                        renameFile.renameTo(new File(currentPath, renamer.getNewName()));
                        File updateDir = new File(currentPath);
                        String updateMas[] = updateDir.list();
                        DefaultListModel updateModel = new DefaultListModel();

                        for (String str : updateMas) {
                            File check = new File(updateDir.getPath(), str);
                            if (!check.isHidden()) {
                                if (check.isDirectory()) {
                                    updateModel.addElement(str);
                                } else {
                                    updateModel.addElement(str);
                                }
                            }
                        }
                        filesList.setModel(updateModel);
                    }
                }
            }
        });

        buttonsPanel.add(backButton);
        buttonsPanel.add(addButton);
        buttonsPanel.add(deleteButton);
        buttonsPanel.add(renameButton);
        buttonsPanel.add(copyButton);
        buttonsPanel.add(enterButton);
        catalogPanel.setLayout(new BorderLayout());
        catalogPanel.add(filesScroll, BorderLayout.CENTER);
        catalogPanel.add(buttonsPanel, BorderLayout.SOUTH);

        getContentPane().add(catalogPanel);
        setSize(600, 600);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }


    public String toFullPath(List<String> file) {
        String listPart = "";
        for (String str : file)
            listPart = listPart + str;
        return listPart;
    }

    public void deleteDir(File file) {
        File[] odjects = file.listFiles();
        if (odjects != null) {
            for (File f : odjects) {
                deleteDir(f);
            }
        }
        file.delete();
    }
    public static void copyFileOrFolder(File source, File dest, CopyOption...  options) throws IOException {
        if (source.isDirectory())
            copyFolder(source, dest, options);
        else {
            ensureParentFolder(dest);
            copyFile(source, dest, options);
        }
    }

    private static void copyFolder(File source, File dest, CopyOption... options) throws IOException {
        if (!dest.exists())
            dest.mkdirs();
        File[] contents = source.listFiles();
        if (contents != null) {
            for (File f : contents) {
                File newFile = new File(dest.getAbsolutePath() + File.separator + f.getName());
                if (f.isDirectory())
                    copyFolder(f, newFile, options);
                else
                    copyFile(f, newFile, options);
            }
        }
    }

    private static void copyFile(File source, File dest, CopyOption... options) throws IOException {
        Files.copy(source.toPath(), dest.toPath(), options);
    }

    private static void ensureParentFolder(File file) {
        File parent = file.getParentFile();
        if (parent != null && !parent.exists())
            parent.mkdirs();
    }


}