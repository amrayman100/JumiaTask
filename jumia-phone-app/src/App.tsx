import { Box } from "@chakra-ui/layout";
import { useColorModeValue } from "@chakra-ui/react";
import { useTranslation } from "react-i18next";
import "./App.css";
import Navbar from "./components/Navbar";
import PhoneBookBase from "./pages/PhoneBookBase";


function App() {

    const bg = useColorModeValue('brandLightGrey', 'brandJumiaBlackBlue')
    const color = useColorModeValue('white', 'gray.800')

    const {
        i18n: { language },
    } = useTranslation();
    const direction = language === 'ar' ? 'rtl' : 'ltr'
    return (
        <>
            <Box bg={bg} height="fit-content" dir={direction} lang={language} padding="0">
                <Navbar />
                <Box mt="20px" bg={bg}><PhoneBookBase /></Box>
            </Box>
        </>
    );
}

export default App;
