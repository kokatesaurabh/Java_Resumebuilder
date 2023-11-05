import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ResumeDAO {
    private Connection connection;

    public ResumeDAO(Connection connection) {
        this.connection = connection;
    }

    public void createResume(Resume resume) {
        String sql = "INSERT INTO resumes (name, email, phone, experience, education) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, resume.getName());
            statement.setString(2, resume.getEmail());
            statement.setString(3, resume.getPhone());
            statement.setInt(4, resume.getExperience());
            statement.setString(5, resume.getEducation());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Resume getResume(int id) {
        String sql = "SELECT * FROM resumes WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Resume resume = new Resume();
                resume.setId(resultSet.getInt("id"));
                resume.setName(resultSet.getString("name"));
                resume.setEmail(resultSet.getString("email"));
                resume.setPhone(resultSet.getString("phone"));
                resume.setExperience(resultSet.getInt("experience"));
                resume.setEducation(resultSet.getString("education"));

                return resume;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Resume> getAllResumes() {
        String sql = "SELECT * FROM resumes";
        List<Resume> resumes = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Resume resume = new Resume();
                resume.setId(resultSet.getInt("id"));
                resume.setName(resultSet.getString("name"));
                resume.setEmail(resultSet.getString("email"));
                resume.setPhone(resultSet.getString("phone"));
                resume.setExperience(resultSet.getInt("experience"));
                resume.setEducation(resultSet.getString("education"));

                resumes.add(resume);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resumes;
    }
}
