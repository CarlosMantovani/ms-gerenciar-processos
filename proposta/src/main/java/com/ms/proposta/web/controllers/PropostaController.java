package com.ms.proposta.web.controllers;

import com.ms.proposta.entities.Proposta;
import com.ms.proposta.repositories.FuncionarioClient;
import com.ms.proposta.services.PropostaService;
import com.ms.proposta.web.dtos.FuncionarioDto;
import com.ms.proposta.web.dtos.PropostaAtualizacaoDto;
import com.ms.proposta.web.dtos.PropostaCadastroDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Propostas", description ="Contém todas as operações relativas aos para cadastro, edição e leitura de uma proposta.")
@RestController
@RequestMapping("api/v1/propostas")
@RequiredArgsConstructor
public class PropostaController {

    @Autowired
    private final PropostaService propostaService;

    private final FuncionarioClient funcionarioClient;

    @Operation(summary = "Cadastrar uma nova proposta", description = "Recurso para cadastrar uma nova proposta",
            responses = {
            @ApiResponse(responseCode = "201", description = "Proposta cadastrada com sucessso",
                        content = @Content(mediaType = "application/json", schema = @Schema(implementation = PropostaCadastroDto.class))),
                    @ApiResponse(responseCode = "409", description = "Proposta ja cadastrada no sistema",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Recurso não processado por dados de entrada inválidos",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @PostMapping
    public ResponseEntity<PropostaCadastroDto> cadastrarProposta(@RequestBody PropostaCadastroDto propostaDto) {
        Proposta proposta = propostaService.cadastrarProposta(propostaDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(propostaDto);
    }

    @Operation(summary = "Listar Propostas", description = "Listar Propostas",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Proposta listada com sucessso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PropostaCadastroDto.class)))
            }
    )
    @GetMapping
    public ResponseEntity<List<Proposta>> listarPropostas() {
        List<Proposta> propostas = propostaService.listarPropostas();
        return ResponseEntity.ok(propostas);
    }

    @Operation(summary = "Recuperar uma proposta pelo id", description = "Recuperar uma proposta pelo  id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Proposta recuperada com sucessso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PropostaCadastroDto.class))),
                    @ApiResponse(responseCode = "404", description = "Recurso não encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<Proposta> buscarPorId(@PathVariable Long id) {
        Proposta proposta = propostaService.buscarPorId(id);
        return ResponseEntity.ok(proposta);
    }

    @Operation(summary = "Atualizar proposta", description = "Atualizar proposta",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Proposta atualizada com sucessso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class))),
                    @ApiResponse(responseCode = "400", description = "Proposta não confere",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "404", description = "Recurso não encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<Proposta> alterarProposta(@PathVariable Long id, @RequestBody PropostaAtualizacaoDto propostaDto) {
        Proposta proposta = propostaService.alterarProposta(id, propostaDto);
        return ResponseEntity.ok(proposta);
    }

    @Operation(summary = "Deletar proposta", description = "Deletar proposta",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Proposta deletada com sucessso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class))),
                    @ApiResponse(responseCode = "404", description = "Recurso não encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarProposta(@PathVariable Long id) {
        propostaService.deletarProposta(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Recuperar um funcionario pelo id", description = "Recuperar um funcionario pelo id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Funcionario recuperado com sucessso",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PropostaCadastroDto.class))),
                    @ApiResponse(responseCode = "404", description = "Recurso não encontrado",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @GetMapping("/funcionarios/{id}")
    public ResponseEntity<FuncionarioDto> buscarFuncionario(@PathVariable Long id) {
        FuncionarioDto funcionario = funcionarioClient.buscarPorId(id);
        return ResponseEntity.ok(funcionario);
    }
}
