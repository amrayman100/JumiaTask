import {Button, useColorMode} from "@chakra-ui/react";
import {BsFillSunFill, BsMoonFill} from "react-icons/bs";

export default function ColorModeToggle() {
    const {colorMode, toggleColorMode} = useColorMode();
    return (
        <header>
            <Button id={"toggleViewMode"} onClick={toggleColorMode} rightIcon={colorMode === "light" ? <BsMoonFill /> : <BsFillSunFill />}>
                Toggle {colorMode === "light" ? "Dark" : "Light"}
            </Button>
        </header>
    );
}
