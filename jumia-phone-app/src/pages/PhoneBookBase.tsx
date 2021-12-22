import { Box, FormControl, HStack, useColorModeValue } from "@chakra-ui/react";
import { useEffect, useState } from "react";
import { useForm } from "react-hook-form";
import { useTranslation } from "react-i18next";
import DropDown from "../components/shared/inputs/base/DropDown";
import Controller from "../components/shared/inputs/Controller";
import TablePaginated from "../components/shared/TablePaginated";
import { PhoneNumber } from "../models/PhoneNumber";
import { useGetPhoneNumbers } from "../service/PhoneBookService";
import { countryOptions, stateOptions } from "./PhoneBookUtils";


type FormValues = {
    state: string;
    country: string;
};

export default function PhoneBookBase() {
    const [page, setPage] = useState(0);
    const [perPage, setPerPage] = useState(5);
    const [countryFiter, setCountryFilter] = useState<string | null>(null);
    const [stateFiter, setStateFilter] = useState<string | null>(null);
    const { t, i18n: { language } } = useTranslation();
    const [phoneNumberList, setPhoneNumberList] = useState<PhoneNumber[]>([]);
    const bg = useColorModeValue('brandLightGrey', 'brandJumiaBlackBlue')
    const [isButtonDisabled, setIsButtonDisabled] = useState(false);

    const {
        register,
        control,
        setValue,
        watch,
        formState: { errors, isValid },
    } = useForm<FormValues>({ mode: 'onChange' });

    const watchCountry = watch("country");
    const watchState = watch("state");

    const {
        data: responseData,
        isSuccess,
        error
    } = useGetPhoneNumbers(page, perPage, stateFiter, countryFiter);

    useEffect(() => {
        setIsButtonDisabled(false);
        responseData && responseData?.phoneNumberList.length > 0 && setPhoneNumberList(responseData?.phoneNumberList);
        if (responseData?.phoneNumberList.length == 1) {
            setPage(page - 0)
        }
    }, [responseData]);

    useEffect(() => {
        isSuccess && setIsButtonDisabled(false);
    }, [isSuccess]);

    useEffect(() => {
        error && setIsButtonDisabled(false);
    }, [error])



    useEffect(() => {
        setPage(0);
        setCountryFilter(watchCountry);
        setStateFilter(watchState)
    }, [watchCountry, watchState])

    const columns =
        [
            {
                Header: t("Name"),
                accessor: "name",
            },
            {
                Header: t("Number"),
                accessor: "number",
            },
            {
                Header: t("Country"),
                accessor: "country",
            },
            {
                Header: t("Code"),
                accessor: "code",
            },
            ,
            {
                Header: t("State"),
                accessor: "state",
            }
        ]


    const filterOptions = () => {
        return <HStack spacing="24px" mb="10">
            <FormControl id={`state`}>
                <Controller
                    {...{
                        control,
                        register,
                        name: "state",
                        rules: {
                            required: false,
                        },
                        render: (props: any) => (
                            <DropDown options={stateOptions} formControl={{ ...props }} label={"State"} />
                        ),
                    }}
                />
            </FormControl>
            <FormControl id={`country`}>
                <Controller
                    {...{
                        control,
                        register,
                        name: "country",
                        rules: {
                            required: false,
                        },
                        render: (props: any) => (
                            <DropDown options={countryOptions} formControl={{ ...props }} label={"Country"} />
                        ),
                    }}
                />
            </FormControl>
        </HStack>
    }

    const disableButtonUtil = () => {
        setIsButtonDisabled(true);
    }

    return (
        <>
            <Box className="h-screen text-gray-500" bg={bg}>
                <Box padding="40px" bg={bg}>
                    {filterOptions()}
                    <Box className="mt-6" bg={bg}>
                        <Box margin="auto" bg={bg}>
                            <TablePaginated
                                disableButton={disableButtonUtil}
                                buttonDisabled={isButtonDisabled}
                                columns={columns}
                                data={phoneNumberList}
                                currentPage={page}
                                perPage={perPage}
                                setPage={setPage}
                                setPerPage={setPerPage}
                            />
                        </Box>
                    </Box>
                </Box>
            </Box>
        </>
    )
}