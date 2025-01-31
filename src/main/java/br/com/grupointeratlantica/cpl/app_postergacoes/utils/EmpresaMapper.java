package br.com.grupointeratlantica.cpl.app_postergacoes.utils;

import br.com.grupointeratlantica.cpl.app_postergacoes.dtos.empresa.EmpresaDTO;
import br.com.grupointeratlantica.cpl.app_postergacoes.models.Empresa;
import org.springframework.stereotype.Component;

@Component
public class EmpresaMapper {

    public Empresa toEntity(EmpresaDTO empresaDTO){
        Empresa empresa = new Empresa();
        empresa.setCodigo(empresaDTO.codigo());
        empresa.setNome(empresaDTO.nome());
        empresa.setEmailCorporativo(empresaDTO.emailCorporativo());
        return empresa;
    }

    public EmpresaDTO toDTO(Empresa empresa){
        return new EmpresaDTO(empresa.getId(), empresa.getCodigo(), empresa.getNome(), empresa.getEmailCorporativo());
    }

}
