package br.com.abruzzo.tqi_backend_evolution_2021.interceptors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Emmanuel Abruzzo
 * @date 06/01/2022
 */
public class InterceptadorAcesso extends HandlerInterceptorAdapter {


    private static final Logger logger = LoggerFactory.getLogger(InterceptadorAcesso.class);

    public static List<Acesso> acessos = new ArrayList<Acesso>();


    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        Acesso acesso = new Acesso();
        acesso.path = request.getRequestURI();
        acesso.data = LocalDateTime.now();


        logarAcesso(acesso);

        request.setAttribute("acesso", acesso);
        return true;
    }

    private void logarAcesso(Acesso acesso) {
        logger.info("Acesso {} \n", acesso.toString());
        if(this.authentication != null)
            logger.info("Dados de autenticação: {} \n",authentication.toString());
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        Acesso acesso = (Acesso)request.getAttribute("acesso");
        acesso.duracao = Duration.between(acesso.data, LocalDateTime.now());
        acessos.add(acesso);

        logarAcesso(acesso);
    }

    public static class Acesso {
        private String path;
        private LocalDateTime data;
        private Duration duracao;
        public String getPath() {
            return path;
        }
        public void setPath(String path) {
            this.path = path;
        }
        public LocalDateTime getData() {
            return data;
        }
        public void setData(LocalDateTime data) {
            this.data = data;
        }
        public Duration getDuracao() {
            return duracao;
        }
        public void setDuracao(Duration duracao) {
            this.duracao = duracao;
        }

        @Override
        public String toString() {
            return "Acesso{" +
                    "path='" + path + '\'' +
                    ", data=" + data +
                    ", duracao=" + duracao +
                    '}';
        }
    }
}

