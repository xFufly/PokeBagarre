package com.montaury.pokebagarre;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import com.montaury.pokebagarre.fixtures.ConstructeurDePokemon;
import com.montaury.pokebagarre.metier.Pokemon;

public class PokemonTests {

    /*
        Valise de tests : 
            Méthode : Pokemon#estVainqueurContre(Pokemon)
                - premier serait vainqueur avec meilleure attaque
                - second serait vainqueur avec meilleure attaque
                - premier serait vainqueur avec meilleure défense et attaque égale au second
                - second serait vainqueur avec meilleure défense et attaque égale au premier
                - premier serait vainqueur avec attaque et défense égale au second
    */

    @Test
    public void premier_serait_vainqueur_avec_meilleure_attaque() {
        // Given
        Pokemon premier = ConstructeurDePokemon.unPokemon().avecAttaque(10).avecDefense(1).construire();
        Pokemon second = ConstructeurDePokemon.unPokemon().avecAttaque(5).avecDefense(2).construire();
        
        // Then
        boolean result = premier.estVainqueurContre(second);
        
        // When
        assertThat(result).isTrue();
    }
    
    @Test
    public void second_serait_vainqueur_avec_meilleure_attaque() {
        // Given
        Pokemon premier = ConstructeurDePokemon.unPokemon().avecAttaque(5).avecDefense(2).construire();
        Pokemon second = ConstructeurDePokemon.unPokemon().avecAttaque(10).avecDefense(1).construire();
        
        // Then
        boolean result = second.estVainqueurContre(premier);

        // When
        assertThat(result).isTrue();
    }

    @Test
    public void premier_serait_vainqueur_avec_meilleure_défense_et_attaque_égale_au_second() {
        // Given
        Pokemon premier = ConstructeurDePokemon.unPokemon().avecAttaque(10).avecDefense(2).construire();
        Pokemon second = ConstructeurDePokemon.unPokemon().avecAttaque(10).avecDefense(1).construire();
        
        // Then
        boolean result = premier.estVainqueurContre(second);

        // When
        assertThat(result).isTrue();
    }

    @Test
    public void second_serait_vainqueur_avec_meilleure_défense_et_attaque_égale_au_premier() {
        // Given
        Pokemon premier = ConstructeurDePokemon.unPokemon().avecAttaque(10).avecDefense(1).construire();
        Pokemon second = ConstructeurDePokemon.unPokemon().avecAttaque(10).avecDefense(2).construire();
        
        // Then
        boolean result = second.estVainqueurContre(premier);

        // When
        assertThat(result).isTrue();
    }

    @Test
    public void premier_serait_vainqueur_avec_attaque_et_défense_égale_au_second() {
        // Given
        Pokemon premier = ConstructeurDePokemon.unPokemon().avecAttaque(10).avecDefense(2).construire();
        Pokemon second = ConstructeurDePokemon.unPokemon().avecAttaque(10).avecDefense(2).construire();
        
        // Then
        boolean result = premier.estVainqueurContre(second);

        // When
        assertThat(result).isTrue();
    }
               
}
