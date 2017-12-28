package com.github.michiruf.tenthousand;

/**
 * @author Michael Ruf
 * @since 2017-12-27
 */
public class RoundAdoptionState {

    public static final RoundAdoptionState NO_ADOPTION = new RoundAdoptionState();

    public final int adoptedPoints;
    public final int adoptedNumberOfDicesRemaining;

    private RoundAdoptionState() {
        adoptedPoints = 0;
        adoptedNumberOfDicesRemaining = 0;
    }

    public RoundAdoptionState(int adoptedPoints, int adoptedNumberOfDicesRemaining) {
        this.adoptedPoints = adoptedPoints;
        this.adoptedNumberOfDicesRemaining = adoptedNumberOfDicesRemaining;
    }

    public boolean isAdoptionAvailable() {
        return adoptedPoints > 0;
    }
}
