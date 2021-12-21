import { ChakraProvider, extendTheme } from "@chakra-ui/react";
import { useTranslation } from "react-i18next";

function ChakraRTLProvider({ children }: any) {
    const {
        i18n: { language },
    } = useTranslation();
    const direction = language === 'ar' ? 'rtl' : 'ltr'

    const customTheme = extendTheme({
        colors: {
            transparent: "transparent",
            black: "#000",
            white: "#fff",
            brandJumiaYellow: "#ffba00",
            brandJumiaBlackBlue: "#1A202C;",
            brandBlue: "#4e73f8",
            brandGrey: "#F1F2F3",
            brandSilver: '#757D8A',
            brandGreyForm: "#f7f7f7",
            brandLightGrey: "#F8F8F8",
            brandGreen: "#07A287",
            brandHollowRed: "#F9E8E8",
            brandRed: "#F43C3C",
            brandHollowGreen: "#07A28714",
            brandLightBrand: "#dae0f7",
            brandDarkGreen: "#08B295",
            gray: {
                50: "#f7fafc",
                900: "#171923",
            },

        },
        // I'm just adding one more fontSize than the default ones
        fontSizes: {
            xxs: "0.625rem",
        },
        // I'm creating a new space tokens since the default is represented with numbers
        space: {
            xs: "0.25rem",
            sm: "0.5rem",
            md: "1rem",
            lg: "1.5rem",
            xl: "2rem",
            xxl: "3rem",
        },
        config: {
            initialColorMode: 'light'
        },
        direction
    });

    return <ChakraProvider theme={customTheme}>{children}</ChakraProvider>
}

export default ChakraRTLProvider;
