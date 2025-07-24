package it.epicode.vinicola_be.security;

import it.epicode.vinicola_be.enumeration.Ruolo;
import it.epicode.vinicola_be.exception.NotFoundException;
import it.epicode.vinicola_be.exception.UnAnauthorizedException;
import it.epicode.vinicola_be.model.Utente;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtTool jwtTool;

    public JwtFilter(JwtTool jwtTool) {
        this.jwtTool = jwtTool;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


        String authorization = request.getHeader("Authorization");

        if(authorization == null || !authorization.startsWith("Bearer ")){
            throw new UnAnauthorizedException("Token non presente, non sei autorizzato ad usare il servizio richiesto");
        }
        else{

            String token = authorization.substring(7);

            jwtTool.ValidateToken(token);
            System.out.println("Token validato: " + token);

            try {

                Utente utente = jwtTool.getUserFromToken(token);
                System.out.println("Utente validato: " + utente.getUsername());

                if(new AntPathMatcher().match("/create", request.getServletPath())){
                    System.out.println("Sono nella creazione con ruolo: " + utente.getRuolo());
                    if(!utente.getRuolo().equals(Ruolo.ADMIN)){
                        throw new UnAnauthorizedException("Utente non autorizzato");
                    }
                }

                Authentication authentication = new UsernamePasswordAuthenticationToken(utente, null, utente.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }catch (NotFoundException e){
                throw new UnAnauthorizedException("Utente collegato al token non trovato");
            }

            System.out.println("Fine autorizzazione");
            filterChain.doFilter(request, response);
        }
    }



    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getServletPath();
        System.out.println("PATH richiesto: " + path);
        return new AntPathMatcher().match("/auth/**", path);
    }
}
