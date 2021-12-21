import '@testing-library/jest-dom'
import { screen, waitFor } from '@testing-library/react'
import { rest } from 'msw'
import { setupServer } from 'msw/node'
import React from 'react'
import { render } from "../TestUtils"
import PhoneBookBase from './PhoneBookBase'


const server = setupServer(
    rest.get('http://localhost:8080/api/v1/numbers', (req, res, ctx) => {
        return res(ctx.json({ "phoneNumberList": [{ "name": "EMILE CHRISTIAN KOUKOU DIKANDA HONORE ", "number": "(237) 697151594", "code": "(237)", "state": "Valid", "country": "Cameroon" }], "numberOfItems": 1 }))
    }),
);

beforeAll(() => server.listen())
afterEach(() => server.resetHandlers())
afterAll(() => server.close())


test('handles get data and displays correctly', async () => {
    render(<PhoneBookBase />)
    const countryName = await waitFor(() => screen.getByText('Cameroon'));
    const name = await waitFor(() => screen.getByText('EMILE CHRISTIAN KOUKOU DIKANDA HONORE'));
    const number = await waitFor(() => screen.getByText('(237) 697151594'));
    expect(countryName).toBeInTheDocument();
    expect(name).toBeInTheDocument();
    expect(number).toBeInTheDocument();
})


test('check that button are clickable', async () => {
    render(<PhoneBookBase />)
    const previousButton = await waitFor(() => screen.getByText('Previous'));
    const nextButton = await waitFor(() => screen.getByText('Next'));

    expect(nextButton).toBeInTheDocument();
    expect(nextButton).toBeEnabled()
    expect(previousButton).toBeInTheDocument();
    expect(previousButton).toBeEnabled()
})