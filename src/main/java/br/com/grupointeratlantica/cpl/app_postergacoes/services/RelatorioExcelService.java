package br.com.grupointeratlantica.cpl.app_postergacoes.services;

import br.com.grupointeratlantica.cpl.app_postergacoes.models.NotaPostergada;

import java.io.File;
import java.util.List;

public interface RelatorioExcelService {

    File gerarRelatorioNotas(List<NotaPostergada> notas);

}
