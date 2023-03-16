/* set background colour to black for light theme, so it doesn't look off with opacity */
export const loginBackgroundImage = {
    container: {
        backgroundColor: '#000',
    },
};

/*-- login page container --*/
export const loginPageContainer = {
    container: {
        flexDirection: 'column',
        justifyContent: 'center',
        alignItems: 'center',
        height: '100%',
    },
};

/*-- login logo image --*/
export const loginLogoImage = {
    image: {
        width: 200,
        height: 102,
    }
};

/*-- login button --*/
export const loginButton = {
    container: {
        backgroundColor: 'transparent',
        borderColor: "white",
    },
};

/*-- fingerprint button --*/
export const fingerprintButton = {
    container: {
        backgroundColor: 'transparent',
        borderColor: "transparent",
        paddingVertical: 0,
        width: 160,
        height: 64,
        alignSelf: 'center',
    },
    icon: {
        size: 100,
        alignSelf: 'center',
    }
};

/*-- login textbox --*/
export const loginTextbox = {
    input: {
        backgroundColor: 'transparent',
        borderColor: "white",
        borderTopWidth: 0,
        borderRightWidth: 0,
        borderLeftWidth: 0,
        borderBottomWidth: 1,
        borderBottomLeftRadius: 0,
        borderBottomRightRadius: 0,
        placeholderTextColor: "white",
        color: "white",
        paddingVertical: 5,
        paddingHorizontal: 0,
    },
};

/*-- divider line --*/
export const dividerLine = {
    container: {
        borderTopWidth: 1,
        borderColor: "white",
    }
};
