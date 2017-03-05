/**
 * Created by kernelith on 25/02/17.
 */

'use strict';

/**
 * Reset the form passed in parameter and remove all field values.
 * @param form Form to reset.
 */
function resetForm(form)
{
    if (form)
    {
        // Each control (input, select, textarea, etc) gets added as a property of the form.
        // The form has other built-in properties as well. However it's easy to filter those out,
        // because the Angular team has chosen to prefix each one with a dollar sign.
        // So, we just avoid those properties that begin with a dollar sign.
        let controlNames = Object.keys(form).filter(key => key.indexOf('$') !== 0);

        // Set each control back to undefined. This is the only way to clear validation messages.
        // Calling `form.$setPristine()` won't do it (even though you wish it would).
        for (let name of controlNames)
        {
            let control = form[name];
            control.$setViewValue(undefined);
            control.$render();  // Force render
        }

        // Reset form
        form.$setPristine();
        form.$setUntouched();
    }
}

/**
 * Reset the given form without touching field values.
 * @param form Form to reset.
 */
function softResetForm(form)
{
    if (form)
    {
        // Reset form
        form.$setPristine();
        form.$setUntouched();
    }
}