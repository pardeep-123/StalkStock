package com.stalkstock.utils.helper;

import android.content.Context;
import android.graphics.Typeface;
import android.text.style.CharacterStyle;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.model.AutocompleteSessionToken;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsResponse;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.util.ArrayList;


public class PlaceAutocompleteAdapterNew extends ArrayAdapter<AutocompletePrediction> implements Filterable

{
    PlacesClient placesClient;
    AutocompleteSessionToken token;
    private static final CharacterStyle STYLE_BOLD = new StyleSpan(Typeface.BOLD);
    private ArrayList<AutocompletePrediction> mResultList = new ArrayList<>();
    private ArrayList<AutocompletePrediction> tempResult = new ArrayList<>();
    Context context;
    private String  TAG="PlaceAutoCompleteAdapter";
    public PlaceAutocompleteAdapterNew(Context context, PlacesClient placesClient, AutocompleteSessionToken token) {
        super(context,android.R.layout.simple_expandable_list_item_1,android.R.id.text1);
//        super(context,R.layout.item_place_search,R.id.autotextfield);
        this.context=context;
        this.placesClient=placesClient;
        this.token=token;
//         this.typeface = context.getResources().getFont(context, R.font.robotomedium);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View row = super.getView(position, convertView, parent);

        AutocompletePrediction item = getItem(position);

        TextView textView1 = (TextView) row.findViewById(android.R.id.text1);
        textView1.setText(item.getPrimaryText(STYLE_BOLD));
        return row;
    }

    @Override
    public int getCount() {
        return mResultList.size();
    }

    @Override
    public AutocompletePrediction getItem(int position) {
        return mResultList.get(position);
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                // Skip the autocomplete query if no constraints are given.
                if (constraint != null) {
                    // Query the autocomplete API for the (constraint) search string.
                    mResultList.clear();
                    mResultList.addAll(getAutoComplete(constraint));
                    if (mResultList != null) {
                        if (mResultList.size() >0) {
                            // The API successfully returned results.
                            results.values = mResultList;
                            results.count = mResultList.size();
                        }
                    }
                }
                return results;

            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    // The API returned at least one result, update the data.
                    notifyDataSetChanged();
                } else {
                    // The API did not return any results, invalidate the data set.
                    notifyDataSetInvalidated();
                }
            }

            @Override
            public CharSequence convertResultToString(Object resultValue) {
                // Override this method to display a readable result in the AutocompleteTextView
                // when clicked.
                if (resultValue instanceof AutocompletePrediction) {
                    return ((AutocompletePrediction) resultValue).getFullText(null);
                } else {
                    return super.convertResultToString(resultValue);
                }
            }
        };
    }

    private ArrayList<AutocompletePrediction> getAutoComplete(CharSequence constraint){
        // Create a new token for the autocomplete session. Pass this to FindAutocompletePredictionsRequest,
        // and once again when the user makes a selection (for example when calling fetchPlace()).
        AutocompleteSessionToken token = AutocompleteSessionToken.newInstance();
        // Create a RectangularBounds object.

        // Use the builder to create a FindAutocompletePredictionsRequest.
        FindAutocompletePredictionsRequest request = FindAutocompletePredictionsRequest.builder()
                .setTypeFilter(TypeFilter.ADDRESS)
                .setSessionToken(token)
                .setQuery(constraint.toString())
                /*.setCountry("us")*/
                .build();

        placesClient.findAutocompletePredictions(request).addOnSuccessListener(new OnSuccessListener<FindAutocompletePredictionsResponse>() {
            @Override
            public void onSuccess(FindAutocompletePredictionsResponse response) {
                for (AutocompletePrediction prediction : response.getAutocompletePredictions()) {
                    Log.i(TAG, prediction.getPrimaryText(null).toString());
                }
                tempResult.clear();
                tempResult.addAll(response.getAutocompletePredictions());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception exception) {
                if (exception instanceof ApiException) {
                    ApiException apiException = (ApiException) exception;
                    Log.e(TAG, "Place not found: " + apiException.getStatusCode());
                }
            }
        });
        return tempResult;
    }
}
