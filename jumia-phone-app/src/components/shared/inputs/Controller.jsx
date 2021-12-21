import {useFormState, useWatch} from "react-hook-form";

const Controller = ({control, register, name, rules, render}) => {
    const value = useWatch({
        control,
        name,
    });
    const {errors} = useFormState({
        control,
        name,
    });
    const props = register(name, rules);

    return render({
        value,
        onChange: e =>
            props.onChange({
                target: {
                    name,
                    value: e,
                },
            }),
        onBlur: props.onBlur,
        name: props.name,
    });
};

export default Controller;
