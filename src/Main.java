public class Main {
    public static void main(String[] args) {
        // Create a ResumeDAO instance using your DatabaseConnection
        ResumeDAO resumeDAO = new ResumeDAO(DatabaseConnection.getConnection());

        // Create the GUI and launch the application
        ResumeBuilderGUI resumeBuilder = new ResumeBuilderGUI(resumeDAO);
    }
}
