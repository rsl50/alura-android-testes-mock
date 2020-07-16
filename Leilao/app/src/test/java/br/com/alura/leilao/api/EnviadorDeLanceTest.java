package br.com.alura.leilao.api;

import android.content.Context;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import br.com.alura.leilao.api.retrofit.client.LeilaoWebClient;
import br.com.alura.leilao.exception.LanceMenorQueUltimoLanceException;
import br.com.alura.leilao.exception.UsuarioJaDeuCincoLancesException;
import br.com.alura.leilao.model.Lance;
import br.com.alura.leilao.model.Leilao;
import br.com.alura.leilao.model.Usuario;
import br.com.alura.leilao.ui.dialog.AvisoDialogManager;

@RunWith(MockitoJUnitRunner.class)
public class EnviadorDeLanceTest {

    @Mock
    private Context context;

    @Mock
    private EnviadorDeLance.LanceProcessadoListener listener;

    @Mock
    private LeilaoWebClient client;

    @Mock
    private AvisoDialogManager manager;

    @Test
    public void deve_MostrarMensagemDeFalha_QuandoLanceForMenorQueUltimoLance() {
        EnviadorDeLance enviador = new EnviadorDeLance(client, listener, context, manager);

        Leilao computador = Mockito.mock(Leilao.class);
        Mockito.doThrow(LanceMenorQueUltimoLanceException.class).when(computador).propoe(ArgumentMatchers.any(Lance.class));

        enviador.envia(computador, new Lance(new Usuario("Fran"), 100));

        Mockito.verify(manager).mostraAvisoLanceMenorQueUltimoLance(context);
    }

    @Test
    public void deve_MostrarMensagemDeFalha_QuandoUsuarioComCincoLancesDerNovoLance() {
        EnviadorDeLance enviador = new EnviadorDeLance(client, listener, context, manager);

        //Mock de um leilao
        Leilao computador = Mockito.mock(Leilao.class);

        //Teste direto da exceção sem necessidade de simular vários lances
        Mockito.doThrow(UsuarioJaDeuCincoLancesException.class).when(computador).propoe(ArgumentMatchers.any(Lance.class));

        enviador.envia(computador, new Lance(new Usuario("Robson"), 200));

        Mockito.verify(manager).mostraAvisoUsuarioJaDeuCincoLances(context);
    }
}