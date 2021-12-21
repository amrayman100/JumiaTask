import {useTranslation} from "react-i18next";
import {
    FaAngleDoubleLeft,
    FaAngleDoubleRight,
    FaAngleLeft,
    FaAngleRight,
    FaFile,
    FaSort,
    FaSortDown,
    FaSortUp,
} from "react-icons/fa";
import {useFilters, useGlobalFilter, usePagination, useSortBy, useTable} from "react-table";
import {Button, GlobalFilterAdvancedSearch, PageButton} from "./Utils";

function Table({columns, data, searchPlaceholder, enableCreate = false, handleCreate = () => {}, createText = "Add"}) {
    // Use the state and functions returned from useTable to build your UI
    const {
        getTableProps,
        getTableBodyProps,
        headerGroups,
        prepareRow,
        page, // Instead of using 'rows', we'll use page,
        // which has only the rows for the active page

        // The rest of these things are super handy, too ;)
        canPreviousPage,
        canNextPage,
        pageOptions,
        pageCount,
        gotoPage,
        nextPage,
        previousPage,
        setPageSize,

        state: {pageIndex, pageSize, globalFilter},
        preGlobalFilteredRows,
        setGlobalFilter,
    } = useTable(
        {
            columns,
            data,
        },
        useFilters, // useFilters!
        useGlobalFilter,
        useSortBy,
        usePagination // new
    );

    const {t} = useTranslation();

    // Render the UI for your table
    return (
        <div className="space-y-8">
            <GlobalFilterAdvancedSearch
                createText={createText}
                enableCreate={enableCreate}
                globalFilter={globalFilter}
                handleCreate={handleCreate}
                headerGroups={headerGroups}
                isAdvancedSearchMode={false}
                preGlobalFilteredRows={preGlobalFilteredRows}
                searchPlaceholder={searchPlaceholder}
                setGlobalFilter={setGlobalFilter}
                setIsAdvancedSearchMode={() => {}}
                enableAdvancedSearch={false}
            />
            {/* table */}
            <div className="flex flex-col mt-4">
                <div className="-mx-4 -my-2 overflow-x-auto sm:-mx-6 lg:-mx-8 Flipped">
                    <div className="inline-block min-w-full py-2 align-middle sm:px-6 lg:px-8 ScrollContent">
                        <div className="overflow-hidden border-b border-gray-200 shadow sm:rounded-md">
                            <table {...getTableProps()} className="min-w-full divide-y divide-gray-200">
                                <thead className="bg-gray-100">
                                    {headerGroups.map(headerGroup => (
                                        <tr {...headerGroup.getHeaderGroupProps()}>
                                            {headerGroup.headers.map(column => (
                                                // Add the sorting props to control sorting. For this example
                                                // we can add them into the header props
                                                <th
                                                    scope="col"
                                                    className="px-6 py-3 text-sm font-semibold tracking-wider text-left text-black group"
                                                    {...column.getHeaderProps(column.getSortByToggleProps())}>
                                                    <div className="flex items-center justify-between">
                                                        {column.render("Header")}
                                                        {/* Add a sort direction indicator */}
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
                                        page.map((row, i) => {
                                            // new
                                            prepareRow(row);
                                            return (
                                                <tr {...row.getRowProps()}>
                                                    {row.cells.map(cell => {
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
                                                <span className="px-3 text-sm text-black">{t("No data found")}</span>
                                            </td>
                                        </tr>
                                    )}
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
            {page.length > 0 && (
                <div className="flex items-center justify-between text-sm">
                    <div className="flex justify-between flex-1 sm:hidden">
                        <Button onClick={() => previousPage()} disabled={!canPreviousPage}>
                            {t("Previous")}
                        </Button>
                        <Button onClick={() => nextPage()} disabled={!canNextPage}>
                            {t("Next")}
                        </Button>
                    </div>
                    <div className="hidden sm:flex-1 sm:flex sm:items-center sm:justify-between">
                        <div className="flex items-baseline gap-x-2">
                            <span className="text-sm text-gray-700">
                                {t("Page")} <span className="font-medium">{pageIndex + 1}</span> {t("of")}{" "}
                                <span className="font-medium">{pageOptions.length}</span>
                            </span>
                            <label>
                                <span className="sr-only">{t("Items Per Page")}</span>
                                <select
                                    className="block w-full py-2 pl-3 pr-10 mt-1 text-sm border-gray-300 rounded-md shadow-sm focus:border-indigo-300 focus:ring focus:ring-indigo-200 focus:ring-opacity-50"
                                    value={pageSize}
                                    onChange={e => {
                                        setPageSize(Number(e.target.value));
                                    }}>
                                    {[5, 10, 20].map(pageSize => (
                                        <option key={pageSize} value={pageSize}>
                                            {t("Show")} {pageSize}
                                        </option>
                                    ))}
                                </select>
                            </label>
                        </div>
                        <div>
                            <nav
                                className="relative z-0 inline-flex -space-x-px text-sm rounded-md shadow-sm"
                                aria-label="Pagination">
                                <PageButton
                                    className="rounded-l-md"
                                    onClick={() => gotoPage(0)}
                                    disabled={!canPreviousPage}>
                                    <span className="sr-only">{t("First")}</span>
                                    <FaAngleDoubleLeft className="w-4 h-4 text-gray-400" aria-hidden="true" />
                                </PageButton>
                                <PageButton onClick={() => previousPage()} disabled={!canPreviousPage}>
                                    <span className="sr-only">Previous</span>
                                    <FaAngleLeft className="w-4 h-4 text-gray-400" aria-hidden="true" />
                                </PageButton>
                                <PageButton onClick={() => nextPage()} disabled={!canNextPage}>
                                    <span className="sr-only">{t("Next")}</span>
                                    <FaAngleRight className="w-4 h-4 text-gray-400" aria-hidden="true" />
                                </PageButton>
                                <PageButton
                                    className="rounded-r-md"
                                    onClick={() => gotoPage(pageCount - 1)}
                                    disabled={!canNextPage}>
                                    <span className="sr-only">{t("Last")}</span>
                                    <FaAngleDoubleRight className="w-4 h-4 text-gray-400" aria-hidden="true" />
                                </PageButton>
                            </nav>
                        </div>
                    </div>
                </div>
            )}
        </div>
    );
}

export default Table;
