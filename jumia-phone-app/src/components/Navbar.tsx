import { Box, Flex, HStack, Image, useColorModeValue } from "@chakra-ui/react";
import jumiaLogoSrc from "../assets/Jumia Group Logo.png";
import ColorModeToggle from "./shared/ColorModeToggle";

export default function Navbar() {
    const navBarBg = useColorModeValue("brandJumiaYellow", "brandJumiaBlackBlue");
    return (
        <>
            <Box as="nav" marginTop="0" position="sticky" top="0" right="0" height="60px" bg={navBarBg} zIndex="1000" borderBottomColor="white" boxShadow="5px 5px 10px rgba(0,0,0, 0.3); ">
                <Box paddingTop="10px" paddingBottom="10px" borderBottomColor="white">
                    <Flex justifyContent="space-between" alignContent="center">
                        <Box><Image width="30%" height="auto" marginInlineStart="15px"
                            src={jumiaLogoSrc} /></Box>
                        <HStack spacing="20px">
                            <Box>
                                <ColorModeToggle />
                            </Box>
                        </HStack>
                    </Flex>
                </Box>
                <div className="sm:hidden">
                    <div className="px-2 pt-2 pb-3 space-y-1"></div>
                </div>
            </Box>
        </>
    );
}
