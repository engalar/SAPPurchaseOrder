import { Appearance } from "react-native";
import { brand, contrast, font} from "../../../theme/styles/native/app/custom-variables";

// Dark Mode - Inherits OS theme if possible
export const darkMode = Appearance.getColorScheme() === "dark";

/*-- full width button --*/
export const fullWidthButton = {
    container: {
        backgroundColor: "transparent",
        borderWidth: 0,
        borderBottomWidth: 1,
        borderColor: contrast.lower,
        paddingHorizontal: 25,
        paddingVertical: 25,
        width: '100%',
        borderRadius: 0,
        flexDirection: 'row-reverse',
        justifyContent: 'space-between', 
    },
    icon: {
        color: darkMode ? "white" : "black",
        size: 20,
    },
    caption: {
        color: darkMode ? "white" : "black",
        fontSize: font.sizeH6,
        fontWeight: "normal",
    },
};

export const rightArrowBtn = { 
    container: {
        backgroundColor: "transparent",
        borderWidth: 0,
        borderRadius: 0,
        width: 10,
    },
    icon: {
        color: darkMode ? "white" : "black",
        size: 10,
    },
};

export const btnWithImage = {
    icon: {
        size: 25,
    },
};

export const uploadImageBtn = {
    container: {
        width: '75%',
    },
};

export const sendIconBtn = {
    container: {
        width: '20%',
    },
};

export const width100Btn = {
    container: {
        width: '100%',
    },
}

export const blueRightArrowBtn = { 
    container: {
        backgroundColor: "transparent",
        borderWidth: 0,
        borderRadius: 0,
        paddingVertical: 0,
        paddingHorizontal: 10,
        minWidth: 20,
        minHeight: 20,
    },
    icon: {
        color: brand.primary,
        size: 15,
    },
};