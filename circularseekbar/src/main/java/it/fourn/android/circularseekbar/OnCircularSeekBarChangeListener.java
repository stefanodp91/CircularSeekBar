package it.fourn.android.circularseekbar;


public interface OnCircularSeekBarChangeListener {

    /**
     * Notification that the point value has changed.
     *
     * @param CircularSeekBar The RoundedSeek view whose value has changed
     * @param progress        The current point value.
     */
    void onProgressChange(CircularSeekBar CircularSeekBar, float progress);

    void onStartTrackingTouch(CircularSeekBar CircularSeekBar);

    void onStopTrackingTouch(CircularSeekBar CircularSeekBar);
}
