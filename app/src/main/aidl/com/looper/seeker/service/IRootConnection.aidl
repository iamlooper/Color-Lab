// IRootConnection.aidl
package com.looper.seeker.service;

// Declare any non-default types here with import statements

interface IRootConnection {
    void changeTheme(
        int primaryAccentColor,
        int secondaryAccentColor,
        int tertiaryAccentColor,
        int primaryNeutralColor,
        int secondaryNeutralColor,
        String style,
        boolean usePreciseColors
    );

    void setEnabled(String packageName, String overlayName, boolean enable);
}