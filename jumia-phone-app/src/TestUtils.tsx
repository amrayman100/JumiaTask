import { ColorModeScript } from "@chakra-ui/react"
import { render } from '@testing-library/react'
import { default as React } from 'react'
import { QueryClient, QueryClientProvider } from "react-query"
import { BrowserRouter } from "react-router-dom"
import ChakraRTLProvider from "./CustomTheme"
import "./i18n/config"
import "./index.css"
const queryClient = new QueryClient();

const AllTheProviders = ({ children }: any) => {
    return (
        <QueryClientProvider client={queryClient}>
            <BrowserRouter>
                <ChakraRTLProvider>
                    <ColorModeScript initialColorMode={"light"} />
                    {children}
                </ChakraRTLProvider>
            </BrowserRouter>
        </QueryClientProvider>
    )
}

const customRender = (ui: any, options?: any) =>
    render(ui, { wrapper: AllTheProviders, ...options })

// re-export everything
export * from '@testing-library/react'
// override render method
export { customRender as render }

