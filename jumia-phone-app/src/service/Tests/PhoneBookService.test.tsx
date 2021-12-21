import '@testing-library/jest-dom';
import { renderHook } from '@testing-library/react-hooks';
import { rest } from 'msw';
import { setupServer } from 'msw/node';
import React from 'react';
import { QueryClient, QueryClientProvider } from "react-query";
import { useGetPhoneNumbers } from "../PhoneBookService";


const queryClient = new QueryClient();
const wrapper = ({ children }: any) => (
    <QueryClientProvider client={queryClient}>
        {children}
    </QueryClientProvider>
);

const server = setupServer(
    rest.get('http://localhost:8080/api/v1/numbers', (req, res, ctx) => {
        return res(ctx.json({ "phoneNumberList": [{ "name": "EMILE CHRISTIAN KOUKOU DIKANDA HONORE ", "number": "(237) 697151594", "code": "(237)", "state": "Valid", "country": "Cameroon" }], "numberOfItems": 1 }))
    }),
);

beforeAll(() => server.listen())
afterEach(() => server.resetHandlers())
afterAll(() => server.close())


test("should return data expected from api successfully", async () => {
    const { result, waitFor } = renderHook(() => useGetPhoneNumbers(0, 1, null, null), { wrapper });
    await waitFor(() => result.current.isSuccess);
    expect(result.current.data?.numberOfItems).toEqual(1);
    expect(result.current.data?.phoneNumberList).toEqual([{ "name": "EMILE CHRISTIAN KOUKOU DIKANDA HONORE ", "number": "(237) 697151594", "code": "(237)", "state": "Valid", "country": "Cameroon" }]);
});