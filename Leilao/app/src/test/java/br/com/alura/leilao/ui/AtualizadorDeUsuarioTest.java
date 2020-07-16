package br.com.alura.leilao.ui;

import android.support.v7.widget.RecyclerView;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import br.com.alura.leilao.database.dao.UsuarioDAO;
import br.com.alura.leilao.model.Usuario;
import br.com.alura.leilao.ui.recyclerview.adapter.ListaLeilaoAdapter;
import br.com.alura.leilao.ui.recyclerview.adapter.ListaUsuarioAdapter;

import static org.junit.Assert.*;

public class AtualizadorDeUsuarioTest {

    @Mock
    private ListaUsuarioAdapter adapter;

    @Mock
    private UsuarioDAO dao;

    @Mock
    private RecyclerView recyclerView;

    @Test
    public void deve_AtualizarListaDeUsuario_QuandoSalvarUsuario(){
        AtualizadorDeUsuario atualizador = new AtualizadorDeUsuario(dao, adapter, recyclerView);

        Usuario robson = new Usuario("Robson");
        atualizador.salva(robson);

        Mockito.verify(dao).salva(robson);
        Mockito.verify(adapter).adiciona(robson);
        Mockito.verify(recyclerView).smoothScrollToPosition(adapter.getItemCount() - 1);
    }
}