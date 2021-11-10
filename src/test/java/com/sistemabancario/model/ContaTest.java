package com.sistemabancario.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.Test;

public class ContaTest {

    @Test
    void testSetNumeroValido() {
        final Conta instance = new Conta();
        final String esperado = "12345-6";
        instance.setNumero(esperado);
        final String obtido = instance.getNumero();
        assertEquals(esperado, obtido);
    }

    @Test
    void testSetNumeroInvalidoNaoArmazena() {
        final Conta instance = new Conta();
        final String invalido = "123";
        assertThrows(IllegalArgumentException.class, () -> instance.setNumero(invalido));
        final String obtido = instance.getNumero();
        assertNotEquals(invalido, obtido);
    }

    @Test
    void testSetNumeroNullInvalido() {
        final Conta instance = new Conta();
        assertThrows(NullPointerException.class, () -> instance.setNumero(null));
    }

    @Test
    void testInstanciaPadraoPoupanca() {
        final Conta instance = new Conta();
        assertFalse(instance.isPoupanca());
    }

    @Test
    void testSetLimiteContaEspecial() {
        final Conta instance = new Conta();
        instance.setEspecial(true);
        final double esperado = 1000;
        instance.setLimite(esperado);
        final double obtido = instance.getLimite();
        assertEquals(esperado, obtido);
    }

    @Test
    void testSetLimiteContaNaoEspecial() {
        final Conta instance = new Conta();
        final double limite = 1000;
        assertThrows(IllegalStateException.class, () -> instance.setLimite(limite));
        final double obtido = instance.getLimite();
        assertNotEquals(limite, obtido);
    }

    @Test
    void testHistoricoNotNull() {
        final Conta instance = new Conta();
        assertNotNull(instance.getMovimentacoes());
    }

    @Test
    void testGetSaldoTotal() {
        final Conta instance = new Conta();
        instance.setEspecial(true);
        final double limite = 500;
        final double esperado = limite;
        instance.setLimite(limite);
        final double obtido = instance.getSaldoTotal();
        assertEquals(esperado, obtido);
    }

    @Test
    void testDepositoDinheiro() {
        final Conta instance = new Conta();
        final double limite = 500.6, deposito = 500.8, esperado = 1001.4;
        instance.setEspecial(true);
        instance.setLimite(limite);
        instance.depositoDinheiro(deposito);
        final double obtido = instance.getSaldoTotal();
        assertEquals(esperado, obtido, 0.001);
    }

    @Test
    void testDepositoDinheiroValorNegativo() {
        final Conta instance = new Conta();
        final double deposito = -842.9;
        assertThrows(IllegalArgumentException.class, () -> instance.depositoDinheiro(deposito));
        final double obtido = instance.getSaldoTotal();
        assertNotEquals(deposito, obtido, 0.001);
    }

    @Test
    void testDepositoDinheiroValorZerado() {
        final Conta instance = new Conta();
        final double deposito = 0;
        assertThrows(IllegalArgumentException.class, () -> instance.depositoDinheiro(deposito));
    }

    @Test
    void testDepositoDinheiroMovimentacaoTipoCredito() {
        final Conta instance = new Conta();
        final char esperado = 'C';
        final double deposito = 200;
        instance.depositoDinheiro(deposito);
        final List<Movimentacao> movimentacoes = instance.getMovimentacoes();
        final Movimentacao ultimaMovimentacao = movimentacoes.get(movimentacoes.size() - 1);
        final double obtido = ultimaMovimentacao.getTipo();
        assertEquals(esperado, obtido);
    }

    @Test
    void testDepositoDinheiroMovimentacaoConfirmada() {
        final Conta instance = new Conta();
        final double deposito = 234;
        instance.depositoDinheiro(deposito);
        final List<Movimentacao> movimentacoes = instance.getMovimentacoes();
        final Movimentacao ultimaMovimentacao = movimentacoes.get(movimentacoes.size() - 1);
        final boolean obtido = ultimaMovimentacao.isConfirmada();
        assertTrue(obtido);
    }

    @Test
    void testDepositoDinheiroMovimentacaoValorAtribuido() {
        final Conta instance = new Conta();
        final double deposito = 40.8;
        final double esperado = deposito;
        instance.depositoDinheiro(deposito);
        final List<Movimentacao> movimentacoes = instance.getMovimentacoes();
        final Movimentacao ultimaMovimentacao = movimentacoes.get(movimentacoes.size() - 1);
        final double obtido = ultimaMovimentacao.getValor();
        assertEquals(esperado, obtido);
    }

    @Test
    void testDepositoDinheiroMovimentacaoRegistrada() {
        final Conta instance = new Conta();
        final double deposito = 40.8;
        final int esperado = 1;
        instance.depositoDinheiro(deposito);
        final int obtido = instance.getMovimentacoes().size();
        assertEquals(esperado, obtido);
    }

    @Test
    void testAddMovimentacaoCredito() {
        final Conta instance = new Conta();
        final Movimentacao movimentacao = new Movimentacao(instance);
        movimentacao.setConfirmada(true);
        movimentacao.setTipo('C');
        final double esperado = 100.50;
        movimentacao.setValor(esperado);
        instance.addMovimentacao(movimentacao);
        assertEquals(esperado, instance.getSaldoTotal());
    }

    @Test
    void testAddMovimentacaoDebito() {
        final Conta instance = new Conta();
        final Movimentacao movimentacao = new Movimentacao(instance);
        movimentacao.setConfirmada(true);
        movimentacao.setTipo('D');
        final double valor = 100.50;
        final double esperado = -valor;
        movimentacao.setValor(valor);
        instance.addMovimentacao(movimentacao);
        assertEquals(esperado, instance.getSaldoTotal());
    }
}
