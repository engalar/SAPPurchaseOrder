import { Appearance, Platform } from "react-native";
import { contrast, font, brand, navigation, background, border } from "../../../theme/styles/native/app/custom-variables";

// Dark Mode - Inherits OS theme if possible
export const darkMode = Appearance.getColorScheme() === "dark";

/*-- page header --*/
export const Layout = {
    header: {
        container: {
            borderBottomWidth: 0,
            ...Platform.select({
                ios: {
                    height: 110,
                },
                android: {
                    height: 60,
                },
            }),
        },
        title: {
            color: font.colorTitle,
            ...Platform.select({
                ios: {
                },
                android: {
                    fontWeight: font.weightLight,
                },
            }),
        },
    },
};

/*-- bottom nav bar --*/
export const navigationStyle = {
    bottomBar: {
        container: {
            backgroundColor: darkMode ? contrast.lower : "white",
            borderTopWidth: 0,
            ...Platform.select({
                ios: {
                    height: 60,
                },
                android: {
                    height: 60,
                    paddingVertical: 10,
                },
            }),
        },
        label: {
            color: darkMode ? "rgba(255,255,255, 0.4)" : navigation.bottomBar.color,
        },
        selectedLabel: {
            color: darkMode ? "white" : brand.primary,
        },
        icon: {
            color: darkMode ? "rgba(255,255,255, 0.4)" : navigation.bottomBar.color,
        },
        selectedIcon: {
            color: darkMode ? "white" : navigation.bottomBar.selectedIconColor,
        },
    },
}

/*-- logo-icon image --*/
export const logoIconImage = {
    container: {
        marginRight: 15,
    },
    image: {
        width: 40,
        height: 20,
    }
};

/*-- user image --*/
export const userImage = {
    image: {
        borderWidth: 1,
        borderColor: darkMode ? '#fff' : contrast.lower,
    }
};

/*-- user image container --*/
export const userImageContainer = {
    container: {
        borderWidth: 1,
        borderColor: darkMode ? '#fff' : contrast.lower,
        paddingHorizontal: 4,
        paddingVertical: 4,
        borderRadius: 44,
    }
};

export const groupboxWrapper = {
    container: {
        backgroundColor: 'transparent',
        borderWidth: 1,
        width: '100%',
        borderRadius: 0,
        borderRightWidth: 0,
        borderLeftWidth: 0,
        borderBottomWidth: 0,
        borderColor: darkMode ? contrast.high : contrast.low,
    },
    header: {
        backgroundColor:  darkMode ? background.gray : "#fff",
        paddingHorizontal: 15,
        paddingVertical: 15,
        borderColor: darkMode ? contrast.high : contrast.low,
    },
    headerContent: {
        color: darkMode ? "#fff" : contrast.high,
        fontSize: font.sizeH5,
        fontWeight: "normal",
    },
    content: {
        borderWidth: 0,
        color: "black",
        fontSize: font.size,
    },
    icon: {
        color: "black",
        size: 50,
    },
}

export const goodsReceiptImage = {
    container: {
        elevation: 4,
        shadowColor: contrast.lower,
        shadowOpacity: 0.8,
        shadowRadius: 8,
        shadowOffset: {
            width: 0,
            height: 2
        },
        marginRight: 5,
        width: '100%',
    },
    image: {
        borderRadius: border.radiusSmall
    }
}

export const headerIcon = {
    container: {
        width: 20,
        height: 20
    }
}