package com.sistemabancario.model;

import static org.junit.jupiter.api.Assertions.*;

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
}
