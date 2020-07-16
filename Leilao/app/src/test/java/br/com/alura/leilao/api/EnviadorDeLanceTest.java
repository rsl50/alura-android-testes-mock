package br.com.alura.leilao.api;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import br.com.alura.leilao.api.retrofit.client.LeilaoWebClient;
import br.com.alura.leilao.api.retrofit.client.RespostaListener;
import br.com.alura.leilao.exception.LanceMenorQueUltimoLanceException;
import br.com.alura.leilao.exception.UsuarioJaDeuCincoLancesException;
import br.com.alura.leilao.model.Lance;
import br.com.alura.leilao.model.Leilao;
import br.com.alura.leilao.model.Usuario;
import br.com.alura.leilao.ui.dialog.AvisoDialogManager;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class EnviadorDeLanceTest {

    @Mock
    private EnviadorDeLance.LanceProcessadoListener listener;

    @Mock
    private LeilaoWebClient client;

    @Mock
    private AvisoDialogManager manager;

    @Mock
    private Leilao leilao = Mockito.mock(Leilao.class);

    @Test
    public void deve_MostrarMensagemDeFalha_QuandoLanceForMenorQueUltimoLance() {
        EnviadorDeLance enviador = new EnviadorDeLance(client, listener, manager);

        doThrow(LanceMenorQueUltimoLanceException.class).when(leilao).propoe(ArgumentMatchers.any(Lance.class));

        enviador.envia(leilao, new Lance(new Usuario("Fran"), 100));

        verify(manager).mostraAvisoLanceMenorQueUltimoLance();
        verify(client, never()).propoe(any(Lance.class), anyLong(), any(RespostaListener.class));
    }

    @Test
    public void deve_MostrarMensagemDeFalha_QuandoUsuarioComCincoLancesDerNovoLance() {
        EnviadorDeLance enviador = new EnviadorDeLance(client, listener, manager);

        //Teste direto da exceção sem necessidade de simular vários lances
        doThrow(UsuarioJaDeuCincoLancesException.class).when(leilao).propoe(ArgumentMatchers.any(Lance.class));

        enviador.envia(leilao, new Lance(new Usuario("Robson"), 200));

        verify(manager).mostraAvisoUsuarioJaDeuCincoLances();
        verify(client, never()).propoe(any(Lance.class), anyLong(), any(RespostaListener.class));
    }
}