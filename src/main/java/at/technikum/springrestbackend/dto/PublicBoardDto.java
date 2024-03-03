package at.technikum.springrestbackend.dto;



import java.util.List;

public class PublicBoardDto {

    private String id;
    private String title;
    private List<PublicUserDto> members;
    private List<PublicUserDto> admins;

    public PublicBoardDto(
            String id,
            String title,
            List<PublicUserDto> members,
            List<PublicUserDto> admins) {
        this.id = id;
        this.title = title;
        this.members = members;
        this.admins = admins;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<PublicUserDto> getMembers() {
        return members;
    }

    public void setMembers(List<PublicUserDto> members) {
        this.members = members;
    }

    public List<PublicUserDto> getAdmins() {
        return admins;
    }

    public void setAdmins(List<PublicUserDto> admins) {
        this.admins = admins;
    }
}
