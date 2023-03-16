import { Appearance } from "react-native";
import { background } from "../../../theme/styles/native/app/custom-variables";

// Dark Mode - Inherits OS theme if possible
export const darkMode = Appearance.getColorScheme() === "dark";

export const sapTile = {
    container : {
        padding: 10,
        borderWidth: 1,
        borderColor: 'transparent',
        borderRadius: 4,
        backgroundColor: darkMode? '#29313A' : '#ffffff',
        shadowColor: "#000",
        shadowOpacity: 0.5,
        shadowRadius: 6,
        shadowOffset: {
            width: 0,
            height: 4
        }
    }
};

/*-- icon in button --*/
export const sapTileImageBtn = {
    container: {
        backgroundColor: "transparent",
        borderWidth: 0,
        borderRadius: 0,
        width: 55,
        height: 55,
        minWidth: undefined,
        minHeight: undefined,
        paddingVertical: 0,
        paddingHorizontal: 0
    },
    icon: {
        color: darkMode ? "white" : "black",
        size: 55,
    },
};

export const arrivalsContainer = { 
    container : {
        padding: 15,
        borderWidth: 1,
        borderColor: 'transparent',
        borderRadius: 4,
        backgroundColor: background.gray,
        shadowColor: "#000",
        shadowOpacity: 0.5,
        shadowRadius: 6,
        shadowOffset: {
            width: 0,
            height: 4
        }
    }
};