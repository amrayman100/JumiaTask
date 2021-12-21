import { Box, FormLabel, Select } from "@chakra-ui/react";
import { useEffect, useState } from "react";
import { useTranslation } from "react-i18next";
import { DropDownOption } from "../../../../models/inputs/DropDownOption";
interface DropDownProps {
    formControl: any;
    options: DropDownOption[];
    label: string;
    error?: any;
}

export default function DropDown({ formControl, options, label, error }: DropDownProps) {
    const [value, setValue] = useState(formControl?.value || "");
    const {
        t,
        i18n: { language },
    } = useTranslation();
    useEffect(() => {
        setValue(formControl.value);
    }, [formControl.value]);
    return (
        <Box>
            <FormLabel fontWeight="600" fontSize="14" as="legend" color="teal">
                {t(`${label}`)}
            </FormLabel>
            <div key={value}>
                <Select
                    fontSize="12"
                    size="lg"
                    placeholder={t("Select option")}
                    backgroundColor="white"
                    defaultValue={value}
                    onChange={vals => {
                        setValue(vals);
                        formControl.onChange && formControl.onChange(vals.target.value);
                    }}
                    isInvalid={error ? true : false}
                    errorBorderColor="red.300">
                    {options.map((option, oid) => (
                        <option key={oid} value={option.value}>
                            {language === "en" ? option.displayEn : option.displayAr}
                        </option>
                    ))}
                </Select>
            </div>
        </Box>
    );
}
