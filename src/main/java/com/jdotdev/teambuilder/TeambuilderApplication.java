package com.jdotdev.teambuilder;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import com.jdotdev.teambuilder.Model.UserRole;
import com.jdotdev.teambuilder.Model.Team;
import com.jdotdev.teambuilder.Model.User;
import com.jdotdev.teambuilder.Repository.TeamRepository;
import com.jdotdev.teambuilder.Repository.UserRepository;
import com.jdotdev.teambuilder.Repository.UserRoleRepository;

@SpringBootApplication
public class TeambuilderApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(TeambuilderApplication.class, args);

	    UserRepository userRepository = context.getBean(UserRepository.class);

        TeamRepository teamRepository = context.getBean(TeamRepository.class);

        UserRoleRepository userRoleRepository = context.getBean(UserRoleRepository.class);
        
	}
}
