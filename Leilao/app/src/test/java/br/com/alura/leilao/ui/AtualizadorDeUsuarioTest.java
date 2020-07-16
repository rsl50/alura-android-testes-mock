package br.com.alura.leilao.ui;

import android.support.v7.widget.RecyclerView;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import br.com.alura.leilao.database.dao.UsuarioDAO;
import br.com.alura.leilao.model.Usuario;
import br.com.alura.leilao.ui.recyclerview.adapter.ListaUsuarioAdapter;

@RunWith(MockitoJUnitRunner.class)
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

        //Simula o retorno de dao.salva para retornar um usuario mockado
        Mockito.when(dao.salva(robson)).thenReturn(new Usuario(1, "Robson"));

        //Manipula o retorno de getItemCount para 1 evitando falha no smoothScrollToPosition
        Mockito.when(adapter.getItemCount()).thenReturn(1);
        atualizador.salva(robson);

        Mockito.verify(dao).salva(new Usuario("Robson"));
        Mockito.verify(adapter).adiciona(new Usuario(1, "Robson"));
        Mockito.verify(recyclerView).smoothScrollToPosition(0);
    }
}