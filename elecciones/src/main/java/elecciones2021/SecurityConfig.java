package elecciones2021;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@EnableTransactionManagement
public class SecurityConfig extends WebSecurityConfigurerAdapter{  //SecurityConfig

	@Autowired
	@Qualifier("customUserDetailsService")
	private UserDetailsService customUserDetailsService;
	 
	
	@Override
	protected void configure(AuthenticationManagerBuilder authManagerBuilder) throws Exception {
		authManagerBuilder.userDetailsService(customUserDetailsService).passwordEncoder(bCryptPasswordEncoder());
	}
	
	@Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	/* http.csrf().disable().
            authorizeRequests().antMatchers("/**").permitAll().anyRequest()
                            .hasRole("USER").and()
                            .formLogin().loginPage("/login").defaultSuccessUrl("/")
                            .permitAll()
                            .failureUrl("/?error=true")
                            .and()
                            .logout().logoutSuccessUrl("/")
                            .deleteCookies("JSESSIONID");
            http.csrf().disable();*/
            
    	http.csrf().disable()
        .authorizeRequests()
          //.antMatchers("/resources/**").permitAll()
          .antMatchers("/css/**", "/js/**", "/img/**", "**/favicon.ico").anonymous()
          .antMatchers("/").hasRole("USER").antMatchers("/dashboard","/resources/static/dashboard/**").hasRole("ADMIN").and().headers().frameOptions().disable().and()
        .formLogin().loginPage("/login").defaultSuccessUrl("/")
        .permitAll()
        .failureUrl("/login?error=true")
        .and()
        .logout().logoutSuccessUrl("/")
        .deleteCookies("JSESSIONID");
            
    	http.csrf().disable();
            
            
            
            
            
            
            
    }
    

}