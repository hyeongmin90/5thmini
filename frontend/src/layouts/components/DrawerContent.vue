<script setup>
import {
  VerticalNavLink,
  VerticalNavSectionTitle,
} from '@layouts'
</script>

<template>
    <ul>
        <VerticalNavSectionTitle :item="{ heading: '작가관리' }" />
        <VerticalNavLink
            :item="{
                title: '작가',
                to: '/authors',
            }"
        />
        <VerticalNavSectionTitle :item="{ heading: '구독자 관리' }" />
        <VerticalNavLink
            :item="{
                title: '구독자',
                to: '/subscribers',
            }"
        />
        <VerticalNavLink
            :item="{
                title: '구독',
                to: '/subscribes',
            }"
        />
        <VerticalNavSectionTitle :item="{ heading: '집필 관리' }" />
        <VerticalNavLink
            :item="{
                title: '원고',
                to: '/manuscripts',
            }"
        />
        <VerticalNavSectionTitle :item="{ heading: '포인트 관리' }" />
        <VerticalNavLink
            :item="{
                title: '포인트',
                to: '/points',
            }"
        />
        <VerticalNavSectionTitle :item="{ heading: '출간 관리' }" />
        <VerticalNavLink
            :item="{
                title: '출간',
                to: '/publishes',
            }"
        />
        <VerticalNavSectionTitle :item="{ heading: '서재플랫폼' }" />
        <VerticalNavLink
            :item="{
                title: '구독책',
                to: '/subscribeBooks',
            }"
        />
        <VerticalNavLink
            :item="{
                title: '출간책',
                to: '/publishBooks',
            }"
        />
    </ul>
</template>
