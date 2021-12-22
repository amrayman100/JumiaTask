import {Button} from "@chakra-ui/react";
import {useEffect, useMemo, useState} from "react";
import {FaAngleLeft, FaAngleRight, FaFile, FaSort, FaSortDown, FaSortUp} from "react-icons/fa";
import {useFilters, useGlobalFilter, usePagination, useSortBy, useTable} from "react-table";

interface TablePaginationProps {
    setPerPage(num: any): void;
    setPage(num: any): void;
    columns: any;
    data: any;
    currentPage: number;
    perPage: number;
    totalPage?: number;
    enableCreate?: false;
    handleCreate?(): void;
    createText?: string;
    buttonDisabled: boolean;
    disableButton(): void;
    id: string;
}

function TablePaginated({
    setPerPage,
    setPage,
    columns,
    data,
    currentPage,
    perPage,
    totalPage,
    buttonDisabled,
    disableButton,
    id,
}: TablePaginationProps) {
    const {
        getTableProps,
        getTableBodyProps,
        headerGroups,
        prepareRow,
        page,
        state: {pageIndex},
    } = useTable(
        {
            columns,
            data,
            useControlledState: state => {
                return useMemo(
                    () => ({
                        ...state,
                        pageIndex: currentPage,
                    }),
                    [state, currentPage]
                );
            },
            initialState: {pageIndex: currentPage},
            manualPagination: true,
            pageCount: totalPage,
        },
        useFilters,
        useGlobalFilter,
        useSortBy,
        usePagination
    );

    const [isLocalButtonDisabled, setIsLocalButtonDisabled] = useState(buttonDisabled);

    useEffect(() => {
        setIsLocalButtonDisabled(buttonDisabled);
    }, [buttonDisabled]);

    return (
        <div className="space-y-8">
            {page.length > 0 && (
                <div className="">
                    <div className="sm:flex-1 sm:flex sm:items-center sm:justify-between">
                        <div className="flex items-baseline gap-x-2">
                            <label>
                                <span className="sr-only">{"Items Per Page"}</span>
                                <select
                                    className="block w-full py-2 pl-3 pr-10 mt-1 text-sm border-gray-300 rounded-md shadow-sm focus:border-indigo-300 focus:ring focus:ring-indigo-200 focus:ring-opacity-50"
                                    value={perPage}
                                    onChange={e => {
                                        setPerPage(Number(e.target.value));
                                    }}>
                                    {[10, 20].map(pageSize => (
                                        <option key={pageSize} value={pageSize}>
                                            {"Show"} {pageSize}
                                        </option>
                                    ))}
                                </select>
                            </label>
                        </div>
                        <div>
                            <nav
                                className="relative z-0 inline-flex -space-x-px text-sm rounded-md shadow-sm mrgin-btn"
                                aria-label="Pagination">
                                <Button
                                    id={"previous"}
                                    mr="10px"
                                    disabled={isLocalButtonDisabled}
                                    onClick={() => {
                                        setPage((s: number) => (s === 0 ? 0 : s - 1));
                                        pageIndex > 0 && disableButton();
                                    }}>
                                    <FaAngleLeft className="w-4 h-4 text-gray-400" aria-hidden="true" />
                                    <span>{"Previous"}</span>
                                </Button>
                                <Button
                                    id={"next"}
                                    disabled={isLocalButtonDisabled}
                                    onClick={() => {
                                        setPage((s: number) => s + 1);
                                        disableButton();
                                    }}>
                                    <span>{"Next"}</span>
                                    <FaAngleRight className="w-4 h-4 text-gray-400" aria-hidden="true" />
                                </Button>
                            </nav>
                        </div>
                    </div>
                </div>
            )}
            {/* table */}
            <div className="flex flex-col mt-4">
                <div className="-mx-4 -my-2 overflow-x-auto sm:-mx-6 lg:-mx-8 Flipped">
                    <div className="inline-block min-w-full py-2 align-middle sm:px-6 ScrollContent">
                        <div className="overflow-hidden border-b border-gray-200 shadow sm:rounded-md">
                            <table {...getTableProps()} className="min-w-full divide-y divide-gray-200" id={id}>
                                <thead className="bg-gray-100">
                                    {headerGroups.map((headerGroup: any) => (
                                        <tr {...headerGroup?.getHeaderGroupProps()}>
                                            {headerGroup.headers.map((column: any) => (
                                                <th
                                                    scope="col"
                                                    className="px-6 py-3 text-sm font-semibold tracking-wider text-left text-black group"
                                                    {...column.getHeaderProps(column.getSortByToggleProps())}>
                                                    <div className="flex items-center justify-between">
                                                        {column.render("Header")}
                                                        <span>
                                                            {column.isSorted ? (
                                                                column.isSortedDesc ? (
                                                                    <FaSortDown className="w-4 h-4 text-gray-400" />
                                                                ) : (
                                                                    <FaSortUp className="w-4 h-4 text-gray-400" />
                                                                )
                                                            ) : (
                                                                <FaSort className="w-4 h-4 text-gray-400 opacity-0 group-hover:opacity-100" />
                                                            )}
                                                        </span>
                                                    </div>
                                                </th>
                                            ))}
                                        </tr>
                                    ))}
                                </thead>
                                <tbody {...getTableBodyProps()} className="bg-white divide-y divide-gray-200">
                                    {(page.length > 0 &&
                                        page.map((row: any, i: any) => {
                                            // new
                                            prepareRow(row);
                                            return (
                                                <tr {...row.getRowProps()}>
                                                    {row.cells.map((cell: any) => {
                                                        return (
                                                            <td
                                                                {...cell.getCellProps()}
                                                                className="px-6 py-4 whitespace-nowrap"
                                                                role="cell">
                                                                <div className="text-sm font-semibold text-gray-500">
                                                                    {cell.render("Cell")}
                                                                </div>
                                                            </td>
                                                        );
                                                    })}
                                                </tr>
                                            );
                                        })) || (
                                        <tr>
                                            <td
                                                colSpan={columns.length}
                                                className="items-center content-center justify-center text-center">
                                                <FaFile />
                                                <span className="px-3 text-sm text-black">{"No data found"}</span>
                                            </td>
                                        </tr>
                                    )}
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default TablePaginated;
