import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class ResumeBuilderGUI {
    private JFrame frame;
    private JTextField nameField, emailField, phoneField, experienceField, educationField;
    private JButton createButton, viewButton;
    private JTextArea resumeTextArea;
    private ResumeDAO resumeDAO;

    public ResumeBuilderGUI(ResumeDAO resumeDAO) {
        this.resumeDAO = resumeDAO;

        frame = new JFrame("Resume Builder");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);

        // Create text fields and buttons
        nameField = new JTextField(20);
        emailField = new JTextField(20);
        phoneField = new JTextField(20);
        experienceField = new JTextField(20);
        educationField = new JTextField(20);

        createButton = new JButton("Create Resume");
        viewButton = new JButton("View Resume");

        resumeTextArea = new JTextArea(10, 30);
        resumeTextArea.setEditable(false);

        // Create a panel with a grid layout to hold form elements
        JPanel formPanel = new JPanel(new GridLayout(7, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        formPanel.add(new JLabel("Name:"));
        formPanel.add(nameField);

        formPanel.add(new JLabel("Email:"));
        formPanel.add(emailField);

        formPanel.add(new JLabel("Phone:"));
        formPanel.add(phoneField);

        formPanel.add(new JLabel("Experience (years):"));
        formPanel.add(experienceField);

        formPanel.add(new JLabel("Education:"));
        formPanel.add(educationField);

        formPanel.add(createButton);
        formPanel.add(viewButton);

        // Add action listeners to the buttons
        createButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Handle create resume action here
                try {
                    String name = nameField.getText();
                    String email = emailField.getText();
                    String phone = phoneField.getText();
                    int experience = Integer.parseInt(experienceField.getText());
                    String education = educationField.getText();
                    Resume newResume = new Resume(name, email, phone, experience, education);
                    resumeDAO.createResume(newResume);
                    JOptionPane.showMessageDialog(frame, "Resume created successfully!");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid input for experience. Please enter a number.");
                }
            }
        });

        viewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Handle view resume action here
                try {
                    int id = Integer.parseInt(JOptionPane.showInputDialog("Enter the resume ID:"));
                    Resume resume = resumeDAO.getResume(id);
                    if (resume != null) {
                        resumeTextArea.setText("Resume ID: " + resume.getId() + "\n" +
                                "Name: " + resume.getName() + "\n" +
                                "Email: " + resume.getEmail() + "\n" +
                                "Phone: " + resume.getPhone() + "\n" +
                                "Experience: " + resume.getExperience() + " years\n" +
                                "Education: " + resume.getEducation());

                        // Generate a PDF from the resume data
                        generatePDF(resume);

                        // Add a "Download PDF" button
                        JButton downloadButton = new JButton("Download PDF");
                        downloadButton.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                // Provide the option to download the PDF
                                openPDFFile("Resume.pdf");
                            }
                        });
                        frame.add(downloadButton, BorderLayout.SOUTH);
                        frame.revalidate();
                    } else {
                        resumeTextArea.setText("Resume not found.");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid input for resume ID. Please enter a number.");
                }
            }
        });

        // Create a panel to hold the form panel and add some color
        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(new Color(220, 220, 220));
        contentPanel.add(formPanel);

        // Create a panel for the text area
        JPanel textPanel = new JPanel();
        textPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        textPanel.add(new JScrollPane(resumeTextArea));

        // Add components to the frame
        frame.setLayout(new BorderLayout());
        frame.add(contentPanel, BorderLayout.NORTH);
        frame.add(textPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    // Generate a PDF from the resume data
    private void generatePDF(Resume resume) {
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream("Resume.pdf"));
            document.open();

            // Add resume data to the PDF
            document.add(new Paragraph("Resume ID: " + resume.getId()));
            document.add(new Paragraph("Name: " + resume.getName()));
            document.add(new Paragraph("Email: " + resume.getEmail()));
            document.add(new Paragraph("Phone: " + resume.getPhone()));
            document.add(new Paragraph("Experience: " + resume.getExperience() + " years"));
            document.add(new Paragraph("Education: " + resume.getEducation()));

            document.close();
            JOptionPane.showMessageDialog(frame, "PDF generated successfully. You can find it as 'Resume.pdf'");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Error generating the PDF.");
        }
    }

    // Open the PDF file
    private void openPDFFile(String filePath) {
        try {
            Desktop.getDesktop().open(new File(filePath));
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(frame, "Error opening the PDF file.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                ResumeBuilderGUI resumeBuilder = new ResumeBuilderGUI(new ResumeDAO(DatabaseConnection.getConnection()));
            }
        });
    }
}
