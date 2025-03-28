package com.balajiseeds.employee.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager2.widget.ViewPager2;

import com.balajiseeds.R;
import com.balajiseeds.admin.adapters.AdapterProductVarietyList;
import com.balajiseeds.admin.models.AddProductVariety.DeleteProductVarietyJson;
import com.balajiseeds.admin.models.AddProductVariety.GetProductVarietyDetails;
import com.balajiseeds.admin.models.AddProductVariety.ProductsVarietiesWithProductsJson;
import com.balajiseeds.databinding.FragmentProductListBinding;
import com.balajiseeds.employee.MyCartActivity;
import com.balajiseeds.employee.adapters.AdapterProducts;
import com.balajiseeds.employee.adapters.ViewPagerAdapter;
import com.balajiseeds.employee.models.ModelProducts;
import com.balajiseeds.employee.models.ModelsProducts;
import com.balajiseeds.employee.models.ProductsVarietiesWithProducts.ProductVarietiesData;
import com.balajiseeds.utils.API;
import com.balajiseeds.utils.WebServices;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;


public class FragmentProductList extends Fragment  {
    FragmentProductListBinding binding;
    WebServices webServices;
    AdapterProducts adapterProducts;
    String varietyId, partyId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentProductListBinding.inflate(inflater, container, false);
        BottomNavigationView botnav = getActivity().findViewById(R.id.botnav_employee_main);
        botnav.getMenu().getItem(2).setChecked(true);

        webServices = new WebServices(getContext());
        binding.tvAddProduct.setVisibility(View.GONE);
        binding.ivCart.setVisibility(View.VISIBLE);
        binding.etSearchEmp.setVisibility(View.GONE);
        binding.ivSearch.setVisibility(View.GONE);


        binding.textView.setText("Product Varieties");

        binding.textView.setOnClickListener(v->{
            requireActivity().getOnBackPressedDispatcher().onBackPressed();
        });

        Bundle bundle = getArguments();
        if (bundle != null) {
             partyId = bundle.getString("partyId");
            // Use the data (e.g., display in TextViews or fetch related products)
            //Toast.makeText(getContext(), "Party Code: " + partyCode + ", Party Name: " + partyName, Toast.LENGTH_SHORT).show();
        }

            webServices.getProductVariety( new WebServices.onGetProductVariety() {
                @Override
                public void getProductVariety(List<GetProductVarietyDetails> ProductVarietyList) {

                    for (GetProductVarietyDetails productVariety : ProductVarietyList) {
                       // binding.tabLayout.addTab(binding.tabLayout.newTab().setText(productVariety.getVarietyName()).setText(productVariety.getVarietyId()));

                        TabLayout.Tab tab = binding.tabLayout.newTab().setText(productVariety.getVarietyName());

                        // Use setTag to store the varietyId in the tab
                        tab.setTag(productVariety.getVarietyId());

                        // Add the tab to the TabLayout
                        binding.tabLayout.addTab(tab);
                    }

                    binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                        @Override
                        public void onTabSelected(TabLayout.Tab tab) {
                            if (tab.getTag() != null) {
                                 varietyId = tab.getTag().toString();
                                Log.d("TabSelected", "Variety ID: " + varietyId);
                                fetchProducts();
                            }
                        }

                        @Override
                        public void onTabUnselected(TabLayout.Tab tab) {
                            // Do nothing

                        }

                        @Override
                        public void onTabReselected(TabLayout.Tab tab) {
                            // Do nothing
                            fetchProducts();

                        }
                    });

                }
            });

        webServices = new WebServices(getContext());
        binding.rvProduct.setLayoutManager(new LinearLayoutManager(getContext()));

        binding.textView.setOnClickListener(v -> {
            requireActivity().getOnBackPressedDispatcher().onBackPressed();
        });
        binding.ivSearch.setOnClickListener(v->{
            fetchProducts();
        });

        adapterProducts=new AdapterProducts(getContext(),true,new ArrayList<>());
        binding.rvProduct.setAdapter(adapterProducts);
        binding.ivCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), MyCartActivity.class);
                intent.putExtra("partyId", partyId);
                startActivity(intent);
                //startActivity(new Intent(getContext(), MyCartActivity.class));
            }
        });

        return binding.getRoot();
    }



    @Override
    public void onStart() {
        super.onStart();
        fetchProducts();
    }

    private void fetchProducts() {
        webServices.fetchProductsVarietiesWithProducts(new ProductsVarietiesWithProductsJson(varietyId, partyId),new WebServices.onGetProduct() {
            @Override
            public void getProduct(String totalItems, List<ProductVarietiesData> productList) {

                if (productList != null && !productList.isEmpty()) {
                    // Show the RecyclerView and update the adapter with the product list
                    binding.rvProduct.setVisibility(View.VISIBLE);
                    binding.tvNoData.setVisibility(View.GONE);
                    adapterProducts.setProductList(productList);

                    binding.Totalitem.setVisibility(View.VISIBLE);
                    binding.tvTotalItems.setText(totalItems);

                    // binding.tvTotalItems.setText("");

                } else {
                    // Hide the RecyclerView and show a "No data" message
                    binding.rvProduct.setVisibility(View.GONE);
                    binding.tvNoData.setVisibility(View.VISIBLE);
                    binding.Totalitem.setVisibility(View.GONE);
                }
                //adapterProducts.setProductList(productList);
            }
        });
    }

  /*  @Override
    public void onTextChange(String newText) {
        if (binding.tvTotalItems != null) {
            binding.tvTotalItems.setText(newText);
        }
    }*/

}