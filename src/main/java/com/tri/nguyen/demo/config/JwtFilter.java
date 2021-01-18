package com.tri.nguyen.demo.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.tri.nguyen.demo.models.entity.common.SecurityProperties;

@Component
public class JwtFilter extends OncePerRequestFilter {
	private final Logger LOG = LoggerFactory.getLogger(JwtFilter.class);

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Autowired
	private SecurityProperties config;

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		String token = req.getHeader(config.getHeader());

		if (token != null && token.startsWith(config.getPrefix())) {
			token = token.replace(config.getPrefix(), "");
			try {
				String subject = jwtTokenProvider.getSubject(token);
				if (!StringUtils.isBlank(subject) && !jwtTokenProvider.isTokenExpired(token)) {
					UsernamePasswordAuthenticationToken auth = this.jwtTokenProvider.getAuthentication(token);
					auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
					SecurityContextHolder.getContext().setAuthentication(auth);
				} else
					throw new Exception();
			} catch (Exception ex) {
				LOG.info("Json web token has expired or invalid.");
				SecurityContextHolder.clearContext();
			}
		}

		chain.doFilter(req, res);
	}

}
