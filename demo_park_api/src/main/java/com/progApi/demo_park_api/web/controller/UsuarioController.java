package com.progApi.demo_park_api.web.controller;

import com.progApi.demo_park_api.entity.Usuario;
import com.progApi.demo_park_api.service.UsuarioService;
import com.progApi.demo_park_api.web.dto.UsuarioCreateDto;
import com.progApi.demo_park_api.web.dto.UsuarioSenhaDto;
import com.progApi.demo_park_api.web.dto.mapper.UsuarioMapper;
import com.progApi.demo_park_api.web.exeption.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Usuarios", description = "Contem todas as operações relativas aos recursos para cadastro, edição e leitura de um usuário.")
@RestController
@RequestMapping("api/v1/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Operation(summary = "Criar um novo  usuário", description = "Recurso para criar um novo usuário.",
            responses = {
            @ApiResponse(responseCode = "201", description = "Recueso criado com sucesso!",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioCreateDto.class))),
            @ApiResponse(responseCode = "409", description = "Usuário já cadastrado no sistema",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "422", description = "Recurso não processado por dados de entrada não processado!",
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
        }

    )
    @PostMapping
    public ResponseEntity<UsuarioCreateDto> create(@Valid @RequestBody UsuarioCreateDto createDto) {
        Usuario user = usuarioService.salvar(UsuarioMapper.toUsuario(createDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(UsuarioMapper.toDto(user));
    }

    @Operation(summary = "Recuperar um usuário pelo id", description = "Recuperar um usuário pelo id.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Recueso recuperado com sucesso!",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioCreateDto.class))),
                    @ApiResponse(responseCode = "404", description = "Recurso não encontrado!",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            }

    )
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioCreateDto> getById(@PathVariable Long id) {
        Usuario user = usuarioService.buscarPorId(id);
        return ResponseEntity.ok(UsuarioMapper.toDto(user));
    }

    @Operation(summary = "Atualizar senha", description = "Atualizar senha",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Senha Atualizada com Sucesso!",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class))),
                    @ApiResponse(responseCode = "400", description = "Senha não confere!",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "404", description = "Recurso não encontrado!",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            }

    )

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updatePassword(@PathVariable Long id, @Valid @RequestBody UsuarioSenhaDto dto) {
        Usuario user = usuarioService.editarSenha(id, dto.getSenhaAtual(), dto.getNovaSenha(), dto.getConfirmaSenha());
        return ResponseEntity.noContent().build();
    }
    @GetMapping
    public ResponseEntity<List<UsuarioCreateDto>> getAll() {
        List<Usuario> users = usuarioService.buscarTodos();
        return ResponseEntity.ok(UsuarioMapper.toListDto(users));
    }
}
