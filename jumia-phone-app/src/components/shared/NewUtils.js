import {Flex} from "@chakra-ui/react";
import {useMemo, useState} from "react";
import {FaPlus, FaSearch} from "react-icons/fa";
import {useAsyncDebounce} from "react-table";

export function classNames(...classes) {
    return classes.filter(Boolean).join(" ");
}

export function Button({children, ...rest}) {
    return (
        <button
            type="button"
            className={classNames(
                "relative inline-flex items-center px-4 py-2 border border-gray-300 text-sm font-medium rounded-md text-gray-700 bg-white hover:bg-gray-50"
            )}
            {...rest}>
            {children}
        </button>
    );
}

export function PageButton({children, ...rest}) {
    return (
        <button
            type="button"
            className={classNames(
                "relative inline-flex items-center px-2 py-2 border border-gray-300 bg-white text-sm font-medium text-gray-500 hover:bg-gray-50"
            )}
            {...rest}>
            {children}
        </button>
    );
}

export function GlobalFilter({
    preGlobalFilteredRows,
    globalFilter,
    setGlobalFilter,
    searchPlaceholder,
    enableCreate,
    handleCreate,
    createText,
    setIsAdvancedSearchMode,
    enableAdvancedSearch,
}) {
    const count = preGlobalFilteredRows.length;
    const [value, setValue] = useState(globalFilter);
    const onChange = useAsyncDebounce(value => {
        setGlobalFilter(value || undefined);
    }, 200);
    const {t} = useTranslation();

    return (
        <div className="flex items-center justify-between">
            <div className="relative">
                <Flex>
                    <div className="absolute top-3 left-3">
                        <FaSearch />
                    </div>
                    <input
                        type="text"
                        className="py-3 pl-10 pr-3 text-sm border-gray-300 rounded-md focus:outline-none"
                        style={{minWidth: 290}}
                        value={value || ""}
                        onChange={e => {
                            setValue(e.target.value);
                            onChange(e.target.value);
                        }}
                        placeholder={searchPlaceholder}
                    />
                </Flex>
            </div>

            {enableCreate && (
                <button style={{width: "auto"}} className="text-sm btn-form-primary" onClick={handleCreate}>
                    <FaPlus className="mr-0 md:mr-3" />
                    <span className="hidden md:inline-block">{createText}</span>
                </button>
            )}
        </div>
    );
}

// This is a custom filter UI for selecting
// a unique option from a list
export function SelectColumnFilter({column: {filterValue, setFilter, preFilteredRows, id, render}}) {
    // Calculate the options for filtering
    // using the preFilteredRows
    const options = useMemo(() => {
        const options = new Set();
        preFilteredRows.forEach(row => {
            options.add(row.values[id]);
        });
        return [...options.values()];
    }, [id, preFilteredRows]);

    // Render a multi-select box
    return (
        <label className="flex items-baseline gap-x-2">
            <span className="text-gray-700">{render("Header")}: </span>
            <select
                className="border-gray-300 rounded-md shadow-sm focus:border-indigo-300 focus:ring focus:ring-indigo-200 focus:ring-opacity-50"
                name={id}
                id={id}
                value={filterValue}
                onChange={e => {
                    setFilter(e.target.value || undefined);
                }}>
                <option value="">All</option>
                {options.map((option, i) => (
                    <option key={i} value={option}>
                        {option}
                    </option>
                ))}
            </select>
        </label>
    );
}

export function GlobalFilterAdvancedSearch({
    isAdvancedSearchMode,
    preGlobalFilteredRows,
    globalFilter,
    setGlobalFilter,
    searchPlaceholder,
    enableCreate,
    handleCreate,
    createText,
    setIsAdvancedSearchMode,
    headerGroups,
    enableAdvancedSearch,
}) {
    return (
        <div className="lg:block sm:flex sm:gap-x-2">
            {!isAdvancedSearchMode && (
                <GlobalFilter
                    preGlobalFilteredRows={preGlobalFilteredRows}
                    globalFilter={globalFilter}
                    setGlobalFilter={setGlobalFilter}
                    searchPlaceholder={searchPlaceholder}
                    enableCreate={enableCreate}
                    handleCreate={handleCreate}
                    createText={createText}
                    enableAdvancedSearch={enableAdvancedSearch}
                    setIsAdvancedSearchMode={setIsAdvancedSearchMode}
                />
            )}
            {headerGroups.map(headerGroup =>
                headerGroup.headers.map(column =>
                    column.Filter ? (
                        <div className="mt-2 sm:mt-0" key={column.id}>
                            {column.render("Filter")}
                        </div>
                    ) : null
                )
            )}
        </div>
    );
}

export function RemoveTime({value: date}) {
    return <span>{date ? date.substring(0, 10) : ""}</span>;
}

export function AvatarCell({value, column, row}) {
    return (
        <div className="flex items-center">
            <div className="flex-shrink-0 w-10 h-10">
                <img className="w-10 h-10 rounded-full" src={row.original[column.imgAccessor]} alt="" />
            </div>
            <div className="ml-4">
                <div className="text-sm font-medium text-gray-900">{value}</div>
                <div className="text-sm text-gray-500">{row.original[column.emailAccessor]}</div>
            </div>
        </div>
    );
}
