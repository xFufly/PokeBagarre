package com.montaury.pokebagarre.metier;

import com.montaury.pokebagarre.erreurs.ErreurMemePokemon;
import com.montaury.pokebagarre.erreurs.ErreurPokemonNonRenseigne;
import com.montaury.pokebagarre.webapi.PokeBuildApi;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.concurrent.CompletableFuture;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;

class BagarreTest {

    private PokeBuildApi mockedApi;

    @BeforeEach
    public void beforeEach() {
        mockedApi = Mockito.mock(PokeBuildApi.class);

        Mockito.when(mockedApi.recupererParNom("jarlin_boussou")).thenReturn(CompletableFuture.completedFuture(
                new Pokemon("jarlin_boussou", "url1", new Stats(4, 2))
        ));

        Mockito.when(mockedApi.recupererParNom("erwan_hoarau")).thenReturn(CompletableFuture.completedFuture(
                new Pokemon("erwan_hoarau", "url1", new Stats(2, 2))
        ));
    }

    @Test
    public void lancement_bagarre_premier_pokemon_serait_invalide() {
        // Given
        Bagarre bagarre = new Bagarre(mockedApi);
        String premier = null;
        String deuxieme = "erwan_hoarau";

        // Then
        Throwable thrown = catchThrowable(() -> bagarre.demarrer(premier, deuxieme));

        // When
        assertThat(thrown)
                .isInstanceOf(ErreurPokemonNonRenseigne.class)
                .hasMessage("Le premier pokemon n'est pas renseigne");
    }

    @Test
    public void lancement_bagarre_deuxieme_pokemon_serait_invalide() {
        // Given
        Bagarre bagarre = new Bagarre(mockedApi);
        String premier = "erwan_hoarau";
        String deuxieme = null;

        // Then
        Throwable thrown = catchThrowable(() -> bagarre.demarrer(premier, deuxieme));

        // When
        assertThat(thrown)
                .isInstanceOf(ErreurPokemonNonRenseigne.class)
                .hasMessage("Le second pokemon n'est pas renseigne");
    }

    @Test
    public void lancement_bagarre_pokemon_identiques() {
        // Given
        Bagarre bagarre = new Bagarre(mockedApi);
        String premier = "erwan_hoarau";
        String deuxieme = "erwan_hoarau";

        // Then
        Throwable thrown = catchThrowable(() -> bagarre.demarrer(premier, deuxieme));

        // When
        assertThat(thrown)
                .isInstanceOf(ErreurMemePokemon.class);
    }

    @Test
    public void premier_pokemon_serait_vainqueur() {
        // Given
        Bagarre bagarre = new Bagarre(mockedApi);
        String premier = "jarlin_boussou";
        String deuxieme = "erwan_hoarau";

        // Then
        Pokemon result = null;
        try {
            result = bagarre.demarrer(premier, deuxieme).get();
        } catch (Exception ignored) {}

        // When
        assertThat(result).isNotNull().satisfies(pokemon -> {
            assertThat(pokemon.getNom()).isEqualTo(premier);
        });
    }

    @Test
    public void deuxieme_pokemon_serait_vainqueur() {
        // Given
        Bagarre bagarre = new Bagarre(mockedApi);
        String premier = "erwan_hoarau";
        String deuxieme = "jarlin_boussou";

        // Then
        Pokemon result = null;
        try {
            result = bagarre.demarrer(premier, deuxieme).get();
        } catch (Exception ignored) {}

        // When
        assertThat(result).isNotNull().satisfies(pokemon -> {
            assertThat(pokemon.getNom()).isEqualTo(deuxieme);
        });
    }

}