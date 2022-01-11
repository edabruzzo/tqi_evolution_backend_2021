package br.com.abruzzo.tqi_backend_evolution_2021.service;

import br.com.abruzzo.tqi_backend_evolution_2021.controller.EmprestimoController;
import br.com.abruzzo.tqi_backend_evolution_2021.dto.ClienteDTO;
import br.com.abruzzo.tqi_backend_evolution_2021.dto.SolicitacaoClienteEmprestimoDTO;
import br.com.abruzzo.tqi_backend_evolution_2021.exceptions.ErroOperacaoTransacionalBancoException;
import br.com.abruzzo.tqi_backend_evolution_2021.model.Cliente;
import br.com.abruzzo.tqi_backend_evolution_2021.model.Role;
import br.com.abruzzo.tqi_backend_evolution_2021.model.Usuario;
import br.com.abruzzo.tqi_backend_evolution_2021.repository.AutenticacaoUsuarioRepository;
import br.com.abruzzo.tqi_backend_evolution_2021.repository.ClienteRepository;
import br.com.abruzzo.tqi_backend_evolution_2021.util.CriptografiaSenha;
import br.com.abruzzo.tqi_backend_evolution_2021.util.EnvioEmailCliente;
import br.com.abruzzo.tqi_backend_evolution_2021.util.VerificacoesSessao;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author Emmanuel Abruzzo
 * @date 06/01/2022
 */
@Service
public class ClienteService {

    private static final Logger logger = LoggerFactory.getLogger(ClienteService.class);


    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    AutenticacaoUsuarioRepository autenticacaoUsuarioRepository;


    public List<ClienteDTO> listarClientes() {

        boolean usuarioLogadoCliente = VerificacoesSessao.verificaSeUsuarioLogadoCliente();

        if(usuarioLogadoCliente)
            return new ArrayList<ClienteDTO>();
        else{
            List<Cliente> listaClientes = this.clienteRepository.findAll();
            List<ClienteDTO> listaClientesDTO = this.converterlistModelToDTO(listaClientes);
            return listaClientesDTO;
        }
    }


    public List<ClienteDTO> converterlistModelToDTO(List<Cliente> listaClientes) {

        List<ClienteDTO> listaClientesDTO = new ArrayList<>();
        listaClientes.stream().forEach(cliente ->{
            ClienteDTO clienteDTO = this.modelMapper.map(cliente,ClienteDTO.class);
            listaClientesDTO.add(clienteDTO);
        });
        return listaClientesDTO;
    }


    public ClienteDTO criaNovoCliente(SolicitacaoClienteEmprestimoDTO solicitacaoClienteEmprestimoDTO) {

            ClienteDTO clienteDTO = new ClienteDTO();
            clienteDTO.setNome(solicitacaoClienteEmprestimoDTO.getNome());
            clienteDTO.setCpf(solicitacaoClienteEmprestimoDTO.getCpf());
            clienteDTO.setRenda(solicitacaoClienteEmprestimoDTO.getRenda());
            clienteDTO.setEnderecoCompleto(solicitacaoClienteEmprestimoDTO.getEnderecoCompleto());
            clienteDTO.setRg(solicitacaoClienteEmprestimoDTO.getRg());

            Cliente cliente = this.converterClienteDTOToModel(clienteDTO);
            Cliente clienteSalvo = this.clienteRepository.save(cliente);
            ClienteDTO clienteSalvoDTO = this.converterModelToDTO(clienteSalvo);
            return clienteSalvoDTO;
    }


    private ClienteDTO converterModelToDTO(Cliente cliente) {
        ClienteDTO clienteDTO = this.modelMapper.map(cliente, ClienteDTO.class);
        return clienteDTO;
    }

    public Cliente converterClienteDTOToModel(ClienteDTO clienteDTO) {
        Cliente cliente = this.modelMapper.map(clienteDTO, Cliente.class);
        return cliente;
    }

    public Cliente findByCPF(String cpf) {
        return this.clienteRepository.findByCpf(cpf);
    }

    public void criaNovoCliente(ClienteDTO clienteDTO) {

        Usuario usuario = this.autenticacaoUsuarioRepository.findByUsername(clienteDTO.getEmail());

        if(usuario == null){

            usuario = new Usuario();
            usuario.setUsername(clienteDTO.getEmail());
            String senhaProvisoria = "SenhaProvisóriaAlterar_";

            String randomNumber = UUID.randomUUID().toString();

            senhaProvisoria += randomNumber;
            usuario.setPassword(CriptografiaSenha.criptografarSenha(senhaProvisoria));
            List<Role> roles = new ArrayList<>();
            roles.add(Role.CLIENTE);
            usuario.setRoles(roles);


            try {
                this.autenticacaoUsuarioRepository.save(usuario);
                EnvioEmailCliente.enviarEmailUsuarioSenhaProvisoria(clienteDTO.getNome(),clienteDTO.getEmail(), usuario.getPassword());
            }catch(Exception exception){
                String mensagemErro = String.format("Não foi possível salvar novo usuário para o cliente {}",clienteDTO);
                throw new ErroOperacaoTransacionalBancoException(mensagemErro,logger);
            }



        }


        Cliente cliente = this.converterClienteDTOToModel(clienteDTO);
        this.clienteRepository.save(cliente);
    }

    public ClienteDTO atualizarCliente(ClienteDTO clienteDTO) {
        Cliente cliente = this.converterClienteDTOToModel(clienteDTO);
        cliente = this.clienteRepository.save(cliente);
        clienteDTO = this.converterModelToDTO(cliente);
        return clienteDTO;
    }

    public ClienteDTO findById(Long id) {
        Cliente cliente = this.clienteRepository.findById(id).get();
        ClienteDTO clienteDTO = this.converterModelToDTO(cliente);
        return clienteDTO;
    }

    public void delete(Long id) {
        Cliente cliente = this.clienteRepository.findById(id).get();
        this.clienteRepository.delete(cliente);
    }
}
