package elecciones2021.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.expression.WebSecurityExpressionRoot;


public class CustomWebSecurityExpressionRoot extends WebSecurityExpressionRoot {

	Properties props;
	
    public CustomWebSecurityExpressionRoot(Authentication a, FilterInvocation fi) {
        super(a, fi);
		props = new Properties();
		try {
			props.load(this.getClass().getClassLoader().getResourceAsStream("toolAccess.properties"));			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    

    public boolean checkAccess(String place) {
    	boolean calculatedValue = false;
    	
    	String strrol = props.getProperty(place.toUpperCase()).toUpperCase();
    	if (strrol.length() > 0)
    	{
    		// Chequea que tenga algun rol permitido para el acceso
    		List<String> lp = Arrays.asList(strrol.split(","));
    		Iterator<? extends GrantedAuthority> it = this.authentication.getAuthorities().iterator();
    	    while(it.hasNext() && !calculatedValue)
    	    {
    	    	GrantedAuthority ga = it.next();
    	    	calculatedValue = lp.contains(ga.getAuthority());
    	    }
    	}
        return calculatedValue;
    }
    
    
    public boolean checkNoAccess(String place) {
    	
        return !checkAccess(place);
    }
    
}
