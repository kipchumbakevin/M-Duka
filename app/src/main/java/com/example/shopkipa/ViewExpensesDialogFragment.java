package com.example.shopkipa;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopkipa.adapters.ViewExpensesAdapter;
import com.example.shopkipa.models.GetExpenseModel;
import com.example.shopkipa.networking.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewExpensesDialogFragment extends DialogFragment {
    private final Context mContext;
    RelativeLayout progressLyt;
    private ArrayList<GetExpenseModel> mExpensesArrayList=new ArrayList<>();
    ViewExpensesAdapter viewExpensesAdapter;

    public ViewExpensesDialogFragment(Context context){
        mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.all_expenses,null);
        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        };
        RecyclerView recyclerView = view.findViewById(R.id.expenses_recyclerView);
        progressLyt = view.findViewById(R.id.progressLoad);
        viewExpensesAdapter = new ViewExpensesAdapter(getActivity(),mExpensesArrayList);
        recyclerView.setAdapter(viewExpensesAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),1));
        viewExpenses();
        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .create();

    }

    private void viewExpenses() {
        showProgress();
            ArrayList<GetExpenseModel> mExpensesArray;
            mExpensesArrayList.clear();
            Call<List<GetExpenseModel>> call = RetrofitClient.getInstance(mContext)
                    .getApiConnector()
                    .getAllExpenses();
            call.enqueue(new Callback<List<GetExpenseModel>>() {
                @Override
                public void onResponse(Call<List<GetExpenseModel>> call, Response<List<GetExpenseModel>> response) {
                    hideProgress();
                    if(response.code()==200){
                        mExpensesArrayList.addAll(response.body());
                        viewExpensesAdapter.notifyDataSetChanged();

                    }
                    else{

                    }
                }

                @Override
                public void onFailure(Call<List<GetExpenseModel>> call, Throwable t) {
                    hideProgress();
                }

            });
    }

    private void hideProgress() {
        progressLyt.setVisibility(View.INVISIBLE);
    }

    private void showProgress() {
        progressLyt.setVisibility(View.VISIBLE);
    }
}
