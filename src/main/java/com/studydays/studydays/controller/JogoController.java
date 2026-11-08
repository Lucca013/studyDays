    package com.studydays.studydays.controller;

    import java.util.List;

    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.GetMapping;
    import org.springframework.web.bind.annotation.PathVariable;
    import org.springframework.web.bind.annotation.PostMapping;
    import org.springframework.web.bind.annotation.RequestMapping;
    import org.springframework.web.bind.annotation.RestController;
    import com.studydays.studydays.model.Decisao;
    import com.studydays.studydays.model.Jogo;
    import com.studydays.studydays.dto.ProcessarEscolhaResponse;
    import com.studydays.studydays.dto.EventoGeradoResponse;
    import com.studydays.studydays.dto.JogoResponse;
    import com.studydays.studydays.service.JogoService;

    @RestController
    @RequestMapping("/jogo")
    public class JogoController {
        private final JogoService service;

        public JogoController(JogoService service){
            this.service = service;
        }

        @PostMapping("/cadastrar/usuario/{usuarioId}")
        public ResponseEntity<Jogo> iniciarNovoJogo(@PathVariable Long usuarioId) {
            Jogo salvo = service.iniciarNovoJogo(usuarioId);
            return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
        }

        @GetMapping("/listar/{jogoId}")
        public ResponseEntity<JogoResponse> listarJogoEspecifico(@PathVariable Long jogoId) {
            JogoResponse resposta = service.listarJogoEspecifico(jogoId);
            return ResponseEntity.ok(resposta);
        }

        @GetMapping("/listar/usuario/{usuarioId}")
        public List<Jogo> listarJogos(@PathVariable Long usuarioId) {
            return service.listarJogos(usuarioId);
        }

        @PostMapping("/avancarEvento/{jogoId}")
        public ResponseEntity<EventoGeradoResponse> avancarEvento(@PathVariable Long jogoId) {
            EventoGeradoResponse resposta = service.avancarEvento(jogoId);
            return ResponseEntity.ok(resposta);
        }

        @GetMapping("/{jogoId}/escolha/{escolhaId}")
        public ResponseEntity<ProcessarEscolhaResponse> processarEscolha(@PathVariable Long jogoId,@PathVariable Long escolhaId) {
            ProcessarEscolhaResponse resposta = service.processarEscolha(jogoId, escolhaId);
            return ResponseEntity.ok(resposta);
        }

        @GetMapping("/{jogoId}/escolhas")
        public ResponseEntity<List<Decisao>> listarDecisoesValidas(@PathVariable Long jogoId) {
            List<Decisao> lista = service.listarDecisoesValidas(jogoId);
            return ResponseEntity.ok(lista);
        }

        @GetMapping("/{jogoId}/dicas")
        public ResponseEntity<String> obterDica(@PathVariable Long jogoId){
            String dica = service.obterDica(jogoId);
            
            if (dica == null) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(dica);
        }
    }
