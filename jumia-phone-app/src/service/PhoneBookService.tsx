import axios from "axios";
import { useQuery } from "react-query";
import { PhoneNumberResponse } from "../models/PhoneNumberResponse";

const API_URL = process.env.REACT_APP_CUSTOMER_SERVICE!;
const axiosApiInstance = axios.create();


function prepareQuerySearchParam(page: number, size: number, state: string | null, country: string | null) {
    let queryParam = `page=${page}&size=${size}`;
    if (country) {
        queryParam = queryParam + `&country=${country}`
    }
    if (state) {
        queryParam = queryParam + `&state=${state}`
    }
    return queryParam;
}


export function useGetPhoneNumbers(page: number, size: number, state: string | null, country: string | null) {
    return useQuery(
        ["numbers", page, size, state, country],
        (): Promise<PhoneNumberResponse> =>
            axiosApiInstance.get(API_URL + `/numbers?${prepareQuerySearchParam(page, size, state, country)}`)
                .then(response => response.data),
        {
            keepPreviousData: true,
        }
    );
}