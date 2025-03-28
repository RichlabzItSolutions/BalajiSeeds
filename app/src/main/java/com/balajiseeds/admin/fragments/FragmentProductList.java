package com.balajiseeds.admin.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.balajiseeds.R;
import com.balajiseeds.admin.AddProductActivity;


import com.balajiseeds.admin.adapters.AdapterProducts;
import com.balajiseeds.admin.models.AddProductVariety.DeleteProductVarietyJson;
import com.balajiseeds.databinding.FragmentProductListBinding;
import com.balajiseeds.employee.models.ModelProducts;
import com.balajiseeds.employee.models.ModelsProducts;
import com.balajiseeds.employee.models.ProductsVarietiesWithProducts.ProductVarietiesData;
import com.balajiseeds.utils.WebServices;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;


public class FragmentProductList extends Fragment {
    FragmentProductListBinding binding;
    WebServices webServices;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentProductListBinding.inflate(inflater, container, false);
        BottomNavigationView botnav = getActivity().findViewById(R.id.botnav_admin_main);
        botnav.getMenu().getItem(2).setChecked(true);
        webServices = new WebServices(getContext());
        binding.rvProduct.setLayoutManager(new GridLayoutManager(getContext(), 2));

        binding.appbar.setVisibility(View.GONE);
        //binding.viewPager.setVisibility(View.GONE);

        binding.LiProduct.setVisibility(View.GONE);

        binding.Totalitem.setVisibility(View.GONE);

        binding.textView.setOnClickListener(v -> {
            requireActivity().getOnBackPressedDispatcher().onBackPressed();
        });
        binding.tvAddProduct.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), AddProductActivity.class));
        });
        binding.ivSearch.setOnClickListener(v->{
            fetchProducts();
        });




        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        fetchProducts();
    }

    private void fetchProducts() {
        String searchStr= String.valueOf(binding.etSearchEmp.getText());

        webServices.getProductsNew(new ModelsProducts.GetProductRequest(searchStr),new WebServices.onGetProducts() {
            @Override
            public void getProducts(List<ModelsProducts.Product> productList) {
                binding.rvProduct.setAdapter(new AdapterProducts(getContext(), productList));

              //  binding.tvTotalItems.setText();

            }

         /*   @Override
            public void getProducts(List<ModelProducts.Product> productList) {
            }*/
        });

       /* webServices.fetchProductsVarietiesWithProducts(new DeleteProductVarietyJson(searchStr),new WebServices.onGetProduct() {
            @Override
            public void getProduct(List<ProductVarietiesData> productList) {
                binding.rvProduct.setAdapter(new AdapterProducts(getContext(), productList));
            }
        });*/

       // getProductsNew
    }
}