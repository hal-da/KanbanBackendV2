package at.technikum.springrestbackend.runner;


import at.technikum.springrestbackend.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AdminUserInitializer implements CommandLineRunner {

    @Autowired
    private AdminService adminService;

    @Override
    public void run(String... args) {
        adminService.createSYSTEMIfNotExists();
        adminService.createAdminIfNotExists();
    }
}