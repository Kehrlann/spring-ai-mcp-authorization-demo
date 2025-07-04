/*
 * Copyright 2025-2025 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.ai.mcp.samples.webflux.httpclient;

import io.modelcontextprotocol.client.McpAsyncClient;
import java.util.List;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.mcp.AsyncMcpToolCallbackProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@SpringBootApplication
@EnableWebFluxSecurity
public class McpWebfluxHttpClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(McpWebfluxHttpClientApplication.class, args);
	}

	@Bean
	ChatClient chatClient(ChatClient.Builder chatClientBuilder, List<McpAsyncClient> mcpClients) {
		return chatClientBuilder.defaultToolCallbacks(new AsyncMcpToolCallbackProvider(mcpClients)).build();
	}

	@Bean
	public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
		http.authorizeExchange(authorize -> authorize.anyExchange().permitAll())
			.oauth2Client(Customizer.withDefaults())
			.csrf(ServerHttpSecurity.CsrfSpec::disable);
		return http.build();
	}

}